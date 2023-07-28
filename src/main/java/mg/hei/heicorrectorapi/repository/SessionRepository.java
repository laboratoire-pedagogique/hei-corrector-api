package mg.hei.heicorrectorapi.repository;

import mg.hei.heicorrectorapi.repository.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<Session, String> {
}
