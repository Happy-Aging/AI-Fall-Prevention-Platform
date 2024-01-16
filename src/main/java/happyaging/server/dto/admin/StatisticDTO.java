package happyaging.server.dto.admin;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StatisticDTO {
    private Long userCount;
    private Long seniorCount;
    private Long surveyCount;

    public static StatisticDTO create(long userCount, long seniorCount, long surveyCount) {
        return StatisticDTO.builder()
                .userCount(userCount)
                .seniorCount(seniorCount)
                .surveyCount(surveyCount)
                .build();
    }
}
