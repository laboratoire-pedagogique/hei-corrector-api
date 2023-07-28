package mg.hei.heicorrectorapi.service;

import java.util.List;
import lombok.AllArgsConstructor;
import mg.hei.heicorrectorapi.model.BoundedPageSize;
import mg.hei.heicorrectorapi.model.PageFromOne;
import mg.hei.heicorrectorapi.repository.TestResultRepository;
import mg.hei.heicorrectorapi.repository.entity.TestResult;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TestResultService {
  private final TestResultRepository repository;


  public List<TestResult> getResultBySessionId(String sessionId) {
    return repository.findBySession_Id(sessionId);
  }

}
