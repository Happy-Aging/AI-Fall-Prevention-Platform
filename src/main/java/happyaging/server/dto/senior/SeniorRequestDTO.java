package happyaging.server.dto.senior;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Getter;

@Getter
public class SeniorRequestDTO {
    @NotNull(message = "이름은 필수입니다")
    private String name;

    private LocalDate birth;

    private String sex;

    @NotNull(message = "주소정보는 필수입니다.")
    private String address;

    private String residence;

    private String profile;
}
