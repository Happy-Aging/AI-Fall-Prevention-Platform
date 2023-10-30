package happyaging.server.dto.senior;

import lombok.Builder;

@Builder
public class SeniorResponseDTO {
    private String name;
    private Integer age;
    private String address;
    private String profile;
    private Integer rank;
}
