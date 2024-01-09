package happyaging.server.dto.survey;

import happyaging.server.domain.option.Option;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class OptionDTO {
    private Long optionId;
    private String content;
    private String image;

    public static OptionDTO create(Option option) {
        return OptionDTO.builder()
                .optionId(option.getId())
                .content(option.getContent())
                .image(option.getImage())
                .build();
    }
}
