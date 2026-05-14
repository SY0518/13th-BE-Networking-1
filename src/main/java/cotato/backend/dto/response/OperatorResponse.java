package cotato.backend.dto.response;

import cotato.backend.entity.Operator;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OperatorResponse {
    private Long id;
    private String name;
    private Integer age;
    private String phoneNumber;
    private String role;

    public static OperatorResponse from(Operator operator) {
        return OperatorResponse.builder()
                .id(operator.getId())
                .name(operator.getName())
                .age(operator.getAge())
                .phoneNumber(operator.getPhoneNumber())
                .role(operator.getRole().getDescription())
                .build();
    }
}
