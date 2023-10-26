package happyaging.server.repository;

import happyaging.server.domain.Senior;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeniorRepository extends JpaRepository<Senior, Long> {
    
}
