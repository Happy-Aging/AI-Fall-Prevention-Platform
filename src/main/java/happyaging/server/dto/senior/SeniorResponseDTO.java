package happyaging.server.dto.senior;

import happyaging.server.domain.senior.Relation;
import happyaging.server.domain.senior.Senior;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class SeniorResponseDTO {
    private Long id;
    private String name;
    private String address;
    private String phoneNumber;
    private LocalDate birth;
    private Relation relation;
    private Integer fallRiskRank;

    public static SeniorResponseDTO create(Senior senior) {
        return SeniorResponseDTO.builder()
                .id(senior.getId())
                .name(senior.getName())
                .address(senior.getAddress())
                .phoneNumber(senior.getPhoneNumber())
                .birth(senior.getBirth())
                .relation(senior.getRelation())
                .fallRiskRank(senior.getLatestSurveyRank())
                .build();
    }
}
