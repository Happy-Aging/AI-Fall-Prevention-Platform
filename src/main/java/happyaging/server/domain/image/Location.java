package happyaging.server.domain.image;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Location {
    BATHROOM("화장실"),
    KITCHEN("주방"),
    ROOM("침실과 거실"),
    ENTRANCE("현관 출입구");

    private String name;

    public String getName() {
        return name;
    }
}
