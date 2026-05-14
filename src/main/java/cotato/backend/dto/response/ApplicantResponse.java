package cotato.backend.dto.response;

import cotato.backend.entity.Applicant;
import cotato.backend.entity.ApplicationDocument;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ApplicantResponse {
    private Long id;
    private String name;
    private Integer period;
    private Integer age;
    private String part;
    private Integer ability;
    private Integer passion;
    private String phoneNumber;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime applicationTime;

    public static ApplicantResponse from(Applicant applicant, ApplicationDocument document) {
        return ApplicantResponse.builder()
                .id(applicant.getId())
                .name(applicant.getName())
                .period(document.getPeriod())
                .age(applicant.getAge())
                .part(document.getPart().getDescription())
                .ability(document.getAbility())
                .passion(document.getPassion())
                .phoneNumber(applicant.getPhoneNumber())
                .applicationTime(document.getApplicationTime())
                .build();
    }
}
