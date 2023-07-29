package mg.hei.heicorrectorapi.service.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SystemUtils {
  private static final int THREAD_POOL = 10;
  private static final ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL);

  private SystemUtils() {

  }

  private static void writeOutputToFile(BufferedReader reader, String logLocation, String fileName)
      throws IOException {
    try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(logLocation, fileName + ".txt"),
        StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
         PrintWriter pw = new PrintWriter(writer)) {
      reader.lines().forEach(pw::println);
    }
  }

  public static void execCmd(String cmd, String fileName) throws IOException, InterruptedException {
    String osName = getOs();
    String[] command = whichOs(osName, cmd);
    Process process = processCmd(command);
    handleCmdResult(process, fileName);
  }

  private static String[] whichOs(String osName, String cmd) {
    if (osName.contains("win")) {
      return new String[] {"cmd", "/c", cmd};
    } else if (osName.contains("nix") || osName.contains("nux") || osName.contains("mac")) {
      return new String[] {"/bin/sh", "-c", cmd};
    } else {
      throw new UnsupportedOperationException("Unsupported operating system: " + osName);
    }
  }

  private static Process processCmd(String[] command) throws IOException {
    ProcessBuilder processBuilder = new ProcessBuilder(command);
    processBuilder.redirectErrorStream(true);
    return processBuilder.start();
  }

  private static void handleCmdResult(Process process, String fileName)
      throws IOException, InterruptedException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

    String logLocation = "output";

    if (process.waitFor() != 0) {
      log.error("Command execution failed.");
    } else {
      log.info("Command executed successfully.");
    }

    writeOutputToFile(reader, logLocation, fileName);

    reader.close();
  }


  public static CompletableFuture<Void> execCmdAsyncAtDir(String cmd, String dir, String fileName) {
    return CompletableFuture.runAsync(() -> {
      try {
        execCmd(String.format("cd %s && %s", dir, cmd), fileName);
      } catch (IOException | InterruptedException e) {
        throw new RuntimeException("Failed to execute command at " + dir, e);
      }
    }, executor);
  }

  public static String getOs() {
    return System.getProperty("os.name").toLowerCase();
  }

}
