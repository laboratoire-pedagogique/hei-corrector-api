package mg.hei.heicorrectorapi.endpoint.mapper;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import mg.hei.heicorrectorapi.rest.model.TestResult;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TestResultMapper {
  private final TestMapper testMapper;

  public TestResult toRest(mg.hei.heicorrectorapi.repository.entity.TestResult domain) {
    return new TestResult()
        .id(domain.getId())
        .studentRef(domain.getStudent())
        .score(domain.getScore())
        .tests(domain.getTests() == null ? List.of() : domain.getTests().stream()
            .map(testMapper::toRest)
            .collect(Collectors.toList()));
  }
}
