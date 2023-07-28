package mg.hei.heicorrectorapi.repository;

import java.util.List;
import mg.hei.heicorrectorapi.repository.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends JpaRepository<Test, String> {
  List<Test> findByResult_Id(String resultId);
}
