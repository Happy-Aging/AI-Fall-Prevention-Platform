package happyaging.server.dto.senior;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SeniorResponseDTO {
    private String name;
    private Integer age;
    private String address;
    private String profile;
    private Integer rank;
}
