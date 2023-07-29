package mg.hei.heicorrectorapi.service;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mg.hei.heicorrectorapi.repository.SessionRepository;
import mg.hei.heicorrectorapi.repository.TestRepository;
import mg.hei.heicorrectorapi.repository.TestResultRepository;
import mg.hei.heicorrectorapi.repository.entity.Session;
import mg.hei.heicorrectorapi.repository.entity.Test;
import mg.hei.heicorrectorapi.repository.entity.TestResult;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class TestService {
  private final TestResultRepository resultRepository;
  private final TestRepository testRepository;
  private final SessionRepository sessionRepository;

  public String installDeps(String baseDir) {
    if (new File(baseDir + "/package.json").exists()) {
      return "npm install";
    } else if (new File(baseDir + "/pom.xml").exists()) {
      return "mvn install";
    } else if (new File(baseDir + "/requirements.txt").exists()) {
      return "pip install -r requirements.txt";
    } else if (new File(baseDir + "/build.gradle").exists()) {
      return "./gradlew build";
    } else {
      return "Package manager not handled.";
    }
  }

  public String checkAndInstallDeps(String baseDir) {
    String installCommand = "";
    String testCommand = checkDepsManager(baseDir);

    boolean depsInstalled = checkDepsAreInstalled(baseDir);

    if (!depsInstalled) {
      installCommand = installDeps(baseDir);
    }

    return installCommand.isEmpty() ? testCommand : installCommand + " && " + testCommand;
  }

  public boolean checkDepsAreInstalled(String baseDir) {
    if (new File(baseDir + "/package.json").exists()) {
      return new File(baseDir + "/node_modules").isDirectory();
    } else if (new File(baseDir + "/pom.xml").exists()) {
      return new File(baseDir + "/target").isDirectory();
    } else if (new File(baseDir + "/requirements.txt").exists()) {
      return true;
    } else if (new File(baseDir + "/build.gradle").exists()) {
      return new File(baseDir + "/build").isDirectory();
    } else {
      return false;
    }
  }

  //TODO: this method should be refactored
  public CompletableFuture<Void> processOutput(Session toTest, File resultDir) {
    return CompletableFuture.runAsync(() -> {
      File[] files = resultDir.listFiles((dir, name) -> name.endsWith(".txt"));

      if (files != null) {
        for (File file : files) {
          try {
            log.info("Processing " + file.getName());
            List<String> lines = Files.lines(Paths.get(file.getAbsolutePath())).toList();

            Integer score = 0;

            TestResult testResult = new TestResult();
            testResult.setSession(toTest);
            testResult.setStudent(file.getName().split("\\.")[0]);
            testResult.setTests(new ArrayList<>());

            TestResult savedTestResult = resultRepository.saveAndFlush(testResult);

            Test test = null;
            for (String line : lines) {
              try {
                if (line.contains("KATA :")) {
                  if (test != null) {
                    testResult.setScore(score);
                    testResult.getTests().add(test);
                    savedTestResult = resultRepository.saveAndFlush(testResult);
                  }

                  String[] splitLine1 = line.split("KATA :");
                  String[] splitLine2 = splitLine1[1].split(" \\(pt:");

                  String kata = splitLine2[0].trim();
                  Integer totalPoints = Integer.parseInt(splitLine2[1].replace(")", ""));

                  // Check if a test for this kata already exists
                  test = testResult.getTests().stream()
                      .filter(t -> t.getKata().equals(kata))
                      .findFirst()
                      .orElse(null);

                  // If a test for this kata does not exist, create a new one
                  if (test == null) {
                    test = new Test();
                    test.setKata(kata);
                    test.setTotalPoints(totalPoints);
                    test.setPassed(0);
                    test.setFailed(0);
                    test.setResult(savedTestResult);

                    testRepository.saveAndFlush(test);
                  }

                } else if (test != null && line.contains("✔")) {
                  test.setResult(savedTestResult);
                  String[] splitLine = line.split("(p:)");
                  Integer points = Integer.parseInt(splitLine[1].replace(")", "").trim());

                  if (line.contains("✔") && test.getPassed() != null) {
                    test.setPassed(test.getPassed() + 1);
                    score += points;
                  }

                  testRepository.saveAndFlush(test);

                } else if (test != null) {
                  if (line.matches("^\\s+\\d+\\) .*")) {
                    if (test.getFailed() != null) {
                      test.setFailed(test.getFailed() + 1);
                    }
                    testRepository.saveAndFlush(test);
                  }
                }
              } catch (Exception e) {
                log.error("Error occurred while processing line: " + line, e);
              }
            }

            if (test != null) {
              testResult.setScore(score);
              testResult.getTests().add(test);
              savedTestResult = resultRepository.saveAndFlush(testResult);

              List<TestResult> sessionTestResults = toTest.getResults();
              if (sessionTestResults == null) {
                sessionTestResults = new ArrayList<>();
              }

              sessionTestResults.add(savedTestResult);
              toTest.setResults(sessionTestResults);

              sessionRepository.saveAndFlush(toTest);
            }
          } catch (IOException e) {
            log.error("Error reading file ", e);
          }
        }
      } else {
        log.warn("No text files found in the result directory.");
      }
    });
  }


  public String checkDepsManager(String baseDir) {
    if (new File(baseDir + "/package.json").exists()) {
      return "npm test";
    } else if (new File(baseDir + "/pom.xml").exists()) {
      return "mvn test";
    } else if (new File(baseDir + "/requirements.txt").exists()) {
      return "pytest";
    } else if (new File(baseDir + "/build.gradle").exists()) {
      return "./gradlew test";
    } else {
      return "Package manager not handled.";
    }
  }

  public List<Test> getResultsByResultId(String resultId) {
    return testRepository.findByResult_Id(resultId);
  }

}
