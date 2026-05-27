package cotato.backend.dto.request;

import cotato.backend.entity.ApplicantPart;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ApplicantCreateRequest {

    @NotBlank(message = "이름은 필수 입력 항목입니다.")
    @Size(min = 2, max = 10, message = "이름은 2글자 이상 10글자 이하여야 합니다.")
    private String name;

    @NotNull(message = "지원 기수는 필수 입력 항목입니다.")
    @Min(value = 1, message = "지원 기수는 1 이상의 정수여야 합니다.")
    private Integer period;

    @NotNull(message = "나이는 필수 입력 항목입니다.")
    @Min(value = 22, message = "나이는 22살 이상이어야 합니다.")
    @Max(value = 30, message = "나이는 30살 이하여야 합니다.")
    private Integer age;

    @NotNull(message = "지원 파트는 필수 입력 항목입니다.")
    private ApplicantPart part;

    @NotNull(message = "실력 점수는 필수 입력 항목입니다.")
    @Min(value = 0, message = "실력 점수는 0 이상이어야 합니다.")
    @Max(value = 10, message = "실력 점수는 10 이하여야 합니다.")
    private Integer ability;

    @NotNull(message = "열정 점수는 필수 입력 항목입니다.")
    @Min(value = 0, message = "열정 점수는 0 이상이어야 합니다.")
    @Max(value = 10, message = "열정 점수는 10 이하여야 합니다.")
    private Integer passion;

    @NotBlank(message = "휴대폰 번호는 필수 입력 항목입니다.")
    @Pattern(regexp = "^010\\d{8}$", message = "휴대폰 번호는 010으로 시작하는 11자리 숫자여야 합니다.")
    private String phoneNumber;

    @NotNull(message = "서류 제출 시간은 필수 입력 항목입니다.")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime applicationTime;
}
