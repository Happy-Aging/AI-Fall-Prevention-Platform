package happyaging.server.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Vendor {

    HAPPYAGING(""),
    KAKAO("https://kapi.kakao.com/v2/user/me");

    private final String url;

    @JsonCreator
    public static Vendor toVendor(String vendor) {
        return Stream.of(Vendor.values())
                .filter(value -> value.toString().equals(vendor))
                .findFirst()
                .orElse(null);
    }
}
