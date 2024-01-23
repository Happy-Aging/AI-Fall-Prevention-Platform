package happyaging.server.dto.admin.survey;

import happyaging.server.domain.survey.Response;
import happyaging.server.domain.senior.Senior;
import happyaging.server.domain.senior.Sex;
import happyaging.server.domain.survey.Survey;
import happyaging.server.domain.user.User;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ExcelDataDTO {
    private Long userId;
    private String userName;
    private Long seniorId;
    private String seniorName;
    private String address;
    private LocalDate birth;
    private Sex sex;
    private LocalDate surveyAt;
    private Integer rank;
    private Map<String, String> responsesByNumber;

    public static ExcelDataDTO create(Survey survey, Integer rank, List<Response> responses) {
        Senior senior = survey.getSenior();
        User user = senior.getUser();

        Map<String, String> responsesByNumber = new HashMap<>();
        responses.forEach(response -> {
            String answer =
                    response.getOption() != null ? response.getOption().getContent() : response.getSubjectiveContext();
            responsesByNumber.put(response.getQuestion().getNumber(), answer);
        });

        return ExcelDataDTO.builder()
                .userId(user == null ? -1 : user.getId())
                .userName(user == null ? "탈퇴한 사용자" : user.getName())
                .seniorId(senior.getId())
                .seniorName(senior.getName())
                .address(senior.getAddress())
                .birth(senior.getBirth())
                .sex(senior.getSex())
                .surveyAt(survey.getDate())
                .rank(rank)
                .responsesByNumber(responsesByNumber)
                .build();
    }
}
