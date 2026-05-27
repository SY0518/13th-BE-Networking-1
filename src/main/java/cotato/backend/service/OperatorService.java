package cotato.backend.service;

import cotato.backend.common.exception.AppException;
import cotato.backend.common.exception.ErrorCode;
import cotato.backend.dto.request.OperatorRequest;
import cotato.backend.dto.response.OperatorResponse;
import cotato.backend.entity.Operator;
import cotato.backend.repository.OperatorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OperatorService {

    private final OperatorRepository operatorRepository;

    @Transactional
    public OperatorResponse createOperator(OperatorRequest request) {
        Operator operator = Operator.builder()
                .name(request.getName())
                .age(request.getAge())
                .phoneNumber(request.getPhoneNumber())
                .role(request.getRole())
                .build();
        return OperatorResponse.from(operatorRepository.save(operator));
    }

    public OperatorResponse getOperator(Long operatorId) {
        Operator operator = operatorRepository.findById(operatorId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        return OperatorResponse.from(operator);
    }

    @Transactional
    public OperatorResponse updateOperator(Long operatorId, OperatorRequest request) {
        Operator operator = operatorRepository.findById(operatorId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        
        operator.update(request.getName(), request.getAge(), request.getPhoneNumber(), request.getRole());
        return OperatorResponse.from(operator);
    }
}
