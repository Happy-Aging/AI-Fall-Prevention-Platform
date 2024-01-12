package happyaging.server.domain.image;

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
}
