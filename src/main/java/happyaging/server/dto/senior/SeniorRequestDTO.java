package happyaging.server.dto.senior;

import happyaging.server.domain.senior.Relation;
import happyaging.server.domain.senior.Sex;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SeniorRequestDTO {
    @NotEmpty(message = "이름은 필수입니다")
    private String name;

    @NotEmpty(message = "주소정보는 필수입니다.")
    private String address;

    @NotNull(message = "출생연도는 필수입니다.")
    private LocalDate birth;

    private String phoneNumber;

    @NotNull(message = "관계는 필수입니다.")
    private Relation relation;

    @NotNull(message = "성별은 필수입니다.")
    private Sex sex;
}
