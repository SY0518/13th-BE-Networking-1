package cotato.backend.controller;

import cotato.backend.common.dto.DataResponse;
import cotato.backend.dto.request.ApplicantCreateRequest;
import cotato.backend.dto.request.ApplicantUpdateRequest;
import cotato.backend.dto.response.ApplicantResponse;
import cotato.backend.dto.response.ApplicantInfoResponse;
import cotato.backend.service.ApplicantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Applicant", description = "지원자 관련 API")
@RestController
@RequestMapping("/api/v1/applicants")
@RequiredArgsConstructor
public class ApplicantController {

    private final ApplicantService applicantService;

    @Operation(summary = "지원서 제출", description = "새로운 지원서를 제출합니다.")
    @PostMapping
    public DataResponse<ApplicantResponse> createApplicant(@RequestBody @Valid ApplicantCreateRequest request) {
        return DataResponse.from(applicantService.createApplicant(request));
    }

    @Operation(summary = "지원자 조회", description = "ID를 이용해 지원자 정보를 조회합니다.")
    @GetMapping("/{applicantId}")
    public DataResponse<ApplicantInfoResponse> getApplicant(@PathVariable Long applicantId) {
        return DataResponse.from(applicantService.getApplicant(applicantId));
    }

    @Operation(summary = "지원자 정보 수정", description = "ID를 이용해 지원자 정보를 수정합니다.")
    @PatchMapping("/{applicantId}")
    public DataResponse<ApplicantInfoResponse> updateApplicant(
            @PathVariable Long applicantId,
            @RequestBody @Valid ApplicantUpdateRequest request) {
        return DataResponse.from(applicantService.updateApplicant(applicantId, request));
    }
}
