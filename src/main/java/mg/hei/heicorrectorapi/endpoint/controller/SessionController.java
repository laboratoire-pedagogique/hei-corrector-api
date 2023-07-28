package mg.hei.heicorrectorapi.endpoint.controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import mg.hei.heicorrectorapi.endpoint.mapper.SessionMapper;
import mg.hei.heicorrectorapi.model.BoundedPageSize;
import mg.hei.heicorrectorapi.model.PageFromOne;
import mg.hei.heicorrectorapi.rest.model.Session;
import mg.hei.heicorrectorapi.service.SessionService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/sessions")
public class SessionController {
  private final SessionService service;
  private final SessionMapper mapper;

  @GetMapping
  public List<Session> getSessions(@RequestParam("page") PageFromOne page,
                                   @RequestParam("page_size")
                                   BoundedPageSize pageSize) {
    return service.getSessions(page, pageSize).stream()
        .map(mapper::toRest)
        .collect(Collectors.toList());
  }

  @GetMapping("/{id}")
  public Session getSession(@PathVariable("id") String id) {
    return mapper.toRest(service.getSessionById(id));
  }

  @GetMapping("/{id}/test")
  public CompletableFuture<Session> runSessionTest(@PathVariable("id") String id) {
    return service.testSession(id).thenApply(mapper::toRest);
  }


  @DeleteMapping("/{id}")
  public String deleteSession(@PathVariable("id") String id) {
    return service.removeSession(id);
  }

  @PutMapping
  public List<Session> crupdateSessions(@RequestBody List<Session> sessions) {
    return service.crupdateSessions(
            sessions.stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList())
        ).stream()
        .map(mapper::toRest)
        .collect(Collectors.toList());
  }


}
