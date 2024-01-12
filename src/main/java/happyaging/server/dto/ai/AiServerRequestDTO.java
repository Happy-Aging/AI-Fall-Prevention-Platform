package happyaging.server.dto.ai;


import happyaging.server.domain.senior.Senior;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AiServerRequestDTO {
    private String name;
    private List<ResponseInfoDTO> data;

    public static AiServerRequestDTO create(Senior senior, List<ResponseInfoDTO> responseInfoDTOS) {
        List<ResponseInfoDTO> data = addSeniorInfo(senior, responseInfoDTOS);
        return AiServerRequestDTO.builder()
                .name(senior.getName())
                .data(data)
                .build();
    }

    private static List<ResponseInfoDTO> addSeniorInfo(Senior senior, List<ResponseInfoDTO> responseInfoDTOS) {
        List<ResponseInfoDTO> data = new ArrayList<>(responseInfoDTOS);
        data.add(ResponseInfoDTO.createBySex(senior.getSex()));
        data.add(ResponseInfoDTO.createByBirth(senior.getBirth()));
        return data;
    }
}
