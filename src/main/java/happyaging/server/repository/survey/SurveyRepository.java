package happyaging.server.repository.survey;

import happyaging.server.domain.survey.Survey;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Long> {

    List<Survey> findAllBySeniorIdOrderByDateDescIdDesc(Long seniorId);

    Page<Survey> findAllBySenior_NameAndSenior_AddressContainingAndSenior_User_NameContainingAndDateBetweenOrderByDateDesc(
            String seniorName, String seniorAddress, String userName, LocalDate startDate, LocalDate endDate,
            Pageable pageable);

    Page<Survey> findAllBySenior_AddressContainingAndSenior_User_NameContainingAndDateBetweenOrderByDateDesc(
            String seniorAddress, String userName, LocalDate startDate, LocalDate endDate, PageRequest pageable);
}
