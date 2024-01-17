package happyaging.server.dto.admin;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PagingResponse<T> {
    private boolean hasNext;
    private List<T> data;
}
