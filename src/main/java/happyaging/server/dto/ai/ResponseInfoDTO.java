package happyaging.server.dto.ai;

import happyaging.server.domain.response.Response;
import happyaging.server.domain.senior.Sex;
import happyaging.server.exception.AppException;
import happyaging.server.exception.errorcode.AppErrorCode;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ResponseInfoDTO {
    private String question;
    private String answer;
    private Double weight;

    public static ResponseInfoDTO create(Response response) {
        return ResponseInfoDTO.builder()
                .question(response.getQuestion().getContent())
                .answer(response.getOption() == null ? response.getSubjectiveContext()
                        : response.getOption().getContent())
                .weight(response.getOption() == null ? -1.0 : response.getOption().getWeight())
                .build();
    }

    public static ResponseInfoDTO createBySex(Sex sex) {
        return ResponseInfoDTO.builder()
                .question("성별은 무엇인가요?")
                .answer(sex.getDescription())
                .weight(sex.getWeight())
                .build();
    }

    public static ResponseInfoDTO createByBirth(LocalDate birth) {
        return ResponseInfoDTO.builder()
                .question("출생년도 언제인가요?")
                .answer(String.valueOf(birth.getYear()))
                .weight(convertBirthToWeight(birth.getYear()))
                .build();
    }

    private static Double convertBirthToWeight(int year) {
        if (year < 1922) {
            return 0.8436;
        }
        if (year > 1962) {
            return 1.1181;
        }

        switch (year) {
            case 1922:
                return 0.8991;
            case 1923:
                return 0.9039;
            case 1924:
                return 0.9039;
            case 1925:
                return 0.9136;
            case 1926:
                return 0.9185;
            case 1927:
                return 0.9234;
            case 1928:
                return 0.9283;
            case 1929:
                return 0.9332;
            case 1930:
                return 0.9382;
            case 1931:
                return 0.9432;
            case 1932:
                return 0.9482;
            case 1933:
                return 0.9533;
            case 1934:
                return 0.9584;
            case 1935:
                return 0.9635;
            case 1936:
                return 0.9686;
            case 1937:
                return 0.9738;
            case 1938:
                return 0.9790;
            case 1939:
                return 0.9842;
            case 1940:
                return 0.9894;
            case 1941:
                return 0.9947;
            case 1942:
                return 1.0;
            case 1943:
                return 1.0053;
            case 1944:
                return 1.0107;
            case 1945:
                return 1.0161;
            case 1946:
                return 1.0215;
            case 1947:
                return 1.0269;
            case 1948:
                return 1.0324;
            case 1949:
                return 1.0379;
            case 1950:
                return 1.0434;
            case 1951:
                return 1.0490;
            case 1952:
                return 1.0546;
            case 1953:
                return 1.0602;
            case 1954:
                return 1.0659;
            case 1955:
                return 1.0715;
            case 1956:
                return 1.0773;
            case 1957:
                return 1.0830;
            case 1958:
                return 1.0888;
            case 1959:
                return 1.0946;
            case 1960:
                return 1.1004;
            case 1961:
                return 1.1063;
            case 1962:
                return 1.1163;
        }
        throw new AppException(AppErrorCode.INVALID_BIRTH);
    }
}
