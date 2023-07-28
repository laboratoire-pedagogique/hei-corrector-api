package mg.hei.heicorrectorapi.endpoint.mapper;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import mg.hei.heicorrectorapi.rest.model.Session;
import mg.hei.heicorrectorapi.rest.model.SessionType;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SessionMapper {
  private final TestResultMapper resultMapper;

  public Session toRest(mg.hei.heicorrectorapi.repository.entity.Session domain) {
    return new Session()
        .id(domain.getId())
        .associatedCourse(domain.getAssociatedCourse())
        .date(domain.getDate())
        .source(domain.getSource())
        .type(SessionType.valueOf(domain.getType()))
        .name(domain.getName())
        .results(domain.getResults().stream()
            .map(resultMapper::toRest)
            .collect(Collectors.toList()));
  }

  public mg.hei.heicorrectorapi.repository.entity.Session toDomain(Session rest) {
    return mg.hei.heicorrectorapi.repository.entity.Session.builder()
        .id(rest.getId())
        .name(rest.getName())
        .date(ZonedDateTime.now())
        .associatedCourse(rest.getAssociatedCourse())
        .source(rest.getSource())
        .type(String.valueOf(rest.getType()))
        .results(List.of())
        .build();
  }
}
