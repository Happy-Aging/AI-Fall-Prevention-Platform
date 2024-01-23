package happyaging.server.dto.admin.senior;

import happyaging.server.domain.image.Location;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReadSeniorImageDTO {
    private Location location;
    private String image;


    public static ReadSeniorImageDTO create(Location location, String image) {
        return ReadSeniorImageDTO.builder()
                .location(location)
                .image(image)
                .build();
    }
}
