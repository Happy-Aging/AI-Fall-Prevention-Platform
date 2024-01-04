package happyaging.server.repository.senior;

import happyaging.server.domain.senior.Senior;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeniorRepository extends JpaRepository<Senior, Long> {

}
