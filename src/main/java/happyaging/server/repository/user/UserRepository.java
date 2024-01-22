package happyaging.server.repository.user;

import happyaging.server.domain.user.User;
import happyaging.server.domain.user.UserType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String userEmail);

    Page<User> findAllByNameContainingAndUserTypeOrderByCreatedAtDesc(String name, UserType userType,
                                                                      Pageable pageable);

    Page<User> findAllByNameContainingOrderByCreatedAtDesc(String name, PageRequest pageable);

    List<User> findAllByNameAndPhoneNumber(String name, String phoneNumber);
}
