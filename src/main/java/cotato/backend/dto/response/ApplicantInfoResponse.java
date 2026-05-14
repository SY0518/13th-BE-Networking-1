package cotato.backend.dto.response;

import cotato.backend.entity.Applicant;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApplicantInfoResponse {
    private Long id;
    private String name;
    private Integer age;
    private String phoneNumber;

    public static ApplicantInfoResponse from(Applicant applicant) {
        return ApplicantInfoResponse.builder()
                .id(applicant.getId())
                .name(applicant.getName())
                .age(applicant.getAge())
                .phoneNumber(applicant.getPhoneNumber())
                .build();
    }
}
