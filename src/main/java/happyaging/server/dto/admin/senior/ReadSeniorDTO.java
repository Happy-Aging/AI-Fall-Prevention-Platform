package happyaging.server.dto.admin.senior;

import happyaging.server.domain.senior.Relation;
import happyaging.server.domain.senior.Senior;
import happyaging.server.domain.senior.Sex;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReadSeniorDTO {
    private String managerName;
    private Long id;
    private String name;
    private String address;
    private String phoneNumber;
    private LocalDate birth;
    private Relation relation;
    private Sex sex;
    private Integer fallRiskRank;

    public static ReadSeniorDTO create(Senior senior) {
        return ReadSeniorDTO.builder()
                .managerName(senior.getUser().getName())
                .id(senior.getUser().getId())
                .name(senior.getName())
                .address(senior.getAddress())
                .phoneNumber(senior.getPhoneNumber())
                .birth(senior.getBirth())
                .relation(senior.getRelation())
                .sex(senior.getSex())
                .fallRiskRank(senior.getLatestSurveyRank())
                .build();
    }
}
