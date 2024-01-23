package happyaging.server.dto.admin.survey;

import happyaging.server.domain.survey.Response;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReadResponseDTO {
    private String number;
    private String content;
    private String answer;

    public static ReadResponseDTO create(Response response) {
        return ReadResponseDTO.builder()
                .number(response.getQuestion().getNumber())
                .content(response.getQuestion().getContent())
                .answer(response.getOption() == null ? response.getSubjectiveContext()
                        : response.getOption().getContent())
                .build();
    }
}
