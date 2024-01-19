package happyaging.server.dto.admin.senior;

import happyaging.server.domain.image.Location;
import happyaging.server.domain.image.SeniorImage;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReadSeniorImageDTO {
    private Location location;
    private List<String> images;


    public static ReadSeniorImageDTO create(Location location, List<SeniorImage> seniorImages) {
        List<String> images = seniorImages.stream()
                .map(SeniorImage::getImage)
                .toList();
        return ReadSeniorImageDTO.builder()
                .location(location)
                .images(images)
                .build();
    }
}
