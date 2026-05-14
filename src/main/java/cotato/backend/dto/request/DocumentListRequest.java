package cotato.backend.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DocumentListRequest {
    private String filterBy;
    private Integer page = 0;
    private Integer pageSize = 10;
}
