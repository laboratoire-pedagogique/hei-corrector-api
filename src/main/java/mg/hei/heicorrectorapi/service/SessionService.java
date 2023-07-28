package mg.hei.heicorrectorapi.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mg.hei.heicorrectorapi.model.BoundedPageSize;
import mg.hei.heicorrectorapi.model.PageFromOne;
import mg.hei.heicorrectorapi.repository.SessionRepository;
import mg.hei.heicorrectorapi.repository.entity.Session;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static mg.hei.heicorrectorapi.service.utils.SystemUtils.execCmdAsyncAtDir;

@Service
@AllArgsConstructor
@Slf4j
public class SessionService {
  private final SessionRepository repository;
  private final TestService testService;

  public List<Session> getSessions(PageFromOne page, BoundedPageSize pageSize) {
    Pageable pageable = PageRequest.of(
        page.getValue() - 1,
        pageSize.getValue());
    return repository.findAll(pageable).toList();
  }

  public Session getSessionById(String id) {
    return repository.findById(id).orElseThrow();
  }

  public CompletableFuture<Session> testSession(String sessionId) {
    Session toTest = repository.findById(sessionId).orElseThrow();

    File baseDir = new File("./srcset/" + toTest.getSource());
    File[] directories = baseDir.listFiles(File::isDirectory);

    if (directories != null) {
      log.info("Found " + directories.length + " directories.");

      List<CompletableFuture<Void>> futures = new ArrayList<>();

      for (File dir : directories) {
        String command = testService.checkAndInstallDeps(dir.getAbsolutePath());
        log.info("Executing command: " + command);

        CompletableFuture<Void> future = execCmdAsyncAtDir(command, dir.getAbsolutePath(), dir.getName())
            .exceptionally(ex -> {
              log.error("Exception executing command: ", ex);
              return null;
            });
        futures.add(future);
      }

      // We must wait that all commands are completed
      CompletableFuture<Void> completedFutures = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));

      File resultDir = new File("./output/result");

      //We fire processOutput after
      return completedFutures.thenApplyAsync((v) -> {
        testService.processOutput(toTest, resultDir).join();
        // re-fetch the session because it was updated
        return repository.findById(sessionId).orElseThrow();
      });

    } else {
      log.warn("No directories found.");
      return CompletableFuture.completedFuture(toTest);
    }
  }


  @Transactional
  public List<Session> crupdateSessions(List<Session> toCrupdate) {
    return repository.saveAllAndFlush(toCrupdate);
  }

  @Transactional
  public String removeSession(String id) {
    repository.deleteById(id);
    return "Session." + id + "has been removed";
  }

}
