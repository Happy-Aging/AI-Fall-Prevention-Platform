package happyaging.server.dto.senior;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Getter;

@Getter
public class SeniorRequestDTO {
    @NotNull(message = "이름은 필수입니다")
    private String name;

    @NotNull(message = "생년월일은 필수입니다.")
    private LocalDate birth;

    @NotNull(message = "성별입력은 필수입니다.")
    private String sex;

    @NotNull(message = "주소정보는 필수입니다.")
    private String address;

    @NotNull(message = "주겨형태는 필수입니다.")
    private String residence;

    private String profile;
}
