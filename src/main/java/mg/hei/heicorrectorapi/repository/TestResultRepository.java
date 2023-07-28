package mg.hei.heicorrectorapi.repository;

import java.util.List;
import mg.hei.heicorrectorapi.repository.entity.TestResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestResultRepository extends JpaRepository<TestResult, String> {
  List<TestResult> findBySession_Id(String sessionId);
}
