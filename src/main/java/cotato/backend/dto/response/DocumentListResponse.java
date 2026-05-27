package cotato.backend.dto.response;

import cotato.backend.entity.ApplicationDocument;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DocumentListResponse {
    private String name;
    private Integer period;
    private String part;
    private Integer likesCount;

    public static DocumentListResponse from(ApplicationDocument document) {
        return DocumentListResponse.builder()
                .name(document.getApplicant().getName())
                .period(document.getPeriod())
                .part(document.getPart().getDescription())
                .likesCount(document.getLikesCount())
                .build();
    }
}
