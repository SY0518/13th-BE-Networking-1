package cotato.backend.controller;

import cotato.backend.common.dto.DataResponse;
import cotato.backend.dto.request.OperatorRequest;
import cotato.backend.dto.response.OperatorResponse;
import cotato.backend.service.OperatorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Operator", description = "운영진 관련 API")
@RestController
@RequestMapping("/api/v1/operators")
@RequiredArgsConstructor
public class OperatorController {

    private final OperatorService operatorService;

    @Operation(summary = "운영진 등록", description = "새로운 운영진을 등록합니다.")
    @PostMapping
    public DataResponse<OperatorResponse> createOperator(@RequestBody @Valid OperatorRequest request) {
        return DataResponse.from(operatorService.createOperator(request));
    }

    @Operation(summary = "운영진 조회", description = "ID를 이용해 운영진 정보를 조회합니다.")
    @GetMapping("/{operatorId}")
    public DataResponse<OperatorResponse> getOperator(@PathVariable Long operatorId) {
        return DataResponse.from(operatorService.getOperator(operatorId));
    }

    @Operation(summary = "운영진 정보 수정", description = "ID를 이용해 운영진 정보를 수정합니다.")
    @PatchMapping("/{operatorId}")
    public DataResponse<OperatorResponse> updateOperator(
            @PathVariable Long operatorId,
            @RequestBody @Valid OperatorRequest request) {
        return DataResponse.from(operatorService.updateOperator(operatorId, request));
    }
}
