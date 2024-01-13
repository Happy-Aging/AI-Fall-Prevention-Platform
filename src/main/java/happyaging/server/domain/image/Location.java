package happyaging.server.domain.image;

import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Location {
    BATHROOM("화장실/욕실"),
    KITCHEN("주방"),
    ROOM("침실/거실"),
    ENTRANCE("현관/출입구");

    private final String name;

    public static Location toLocation(String location) {
        return Stream.of(Location.values())
                .filter(value -> value.name.equals(location))
                .findFirst()
                .orElse(null);
    }
}
