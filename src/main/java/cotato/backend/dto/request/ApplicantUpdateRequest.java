package cotato.backend.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApplicantUpdateRequest {

    @NotBlank(message = "이름은 필수 입력 항목입니다.")
    private String name;

    @NotNull(message = "나이는 필수 입력 항목입니다.")
    @Min(22) @Max(30)
    private Integer age;

    @NotBlank(message = "휴대폰 번호는 필수 입력 항목입니다.")
    @Pattern(regexp = "^010\\d{8}$")
    private String phoneNumber;
}
