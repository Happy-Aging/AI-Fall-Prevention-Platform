package happyaging.server.dto.senior;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SeniorResponseDTO {
    private Long id;
    private String name;
    private Integer age;
    private String address;
    private String profile;
    private Integer rank;
}
