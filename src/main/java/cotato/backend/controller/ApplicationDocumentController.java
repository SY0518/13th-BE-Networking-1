package cotato.backend.controller;

import cotato.backend.common.dto.DataResponse;
import cotato.backend.dto.request.DocumentListRequest;
import cotato.backend.dto.response.ApplicantResponse;
import cotato.backend.dto.response.DocumentListResponse;
import cotato.backend.service.ApplicationDocumentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Application Document", description = "지원 서류 관련 API")
@RestController
@RequestMapping("/api/v1/documents")
@RequiredArgsConstructor
public class ApplicationDocumentController {

    private final ApplicationDocumentService documentService;

    @Operation(summary = "서류 상세 조회", description = "ID를 이용해 서류의 상세 정보를 조회합니다.")
    @GetMapping("/{documentId}")
    public DataResponse<ApplicantResponse> getDocument(@PathVariable Long documentId) {
        return DataResponse.from(documentService.getDocument(documentId));
    }

    @Operation(summary = "서류 리스트 조회", description = "필터링 및 페이징을 통해 서류 리스트를 조회합니다.")
    @GetMapping
    public DataResponse<List<DocumentListResponse>> listDocuments(@ModelAttribute DocumentListRequest request) {
        return DataResponse.from(documentService.listDocuments(request));
    }

    @Operation(summary = "좋아요 토글", description = "지원 서류에 좋아요를 누르거나 취소합니다.")
    @PostMapping("/{documentId}/likes")
    public DataResponse<Void> toggleLike(
            @PathVariable Long documentId,
            @RequestParam Long operatorId) {
        documentService.toggleLike(documentId, operatorId);
        return DataResponse.from(null);
    }
}
