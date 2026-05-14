package cotato.backend.dto.request;

import cotato.backend.entity.OperatorRole;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OperatorRequest {

    @NotBlank(message = "이름은 필수 입력 항목입니다.")
    private String name;

    @NotNull(message = "나이는 필수 입력 항목입니다.")
    private Integer age;

    @NotBlank(message = "휴대폰 번호는 필수 입력 항목입니다.")
    private String phoneNumber;

    @NotNull(message = "역할은 필수 입력 항목입니다.")
    private OperatorRole role;
}
