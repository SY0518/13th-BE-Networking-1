package cotato.backend.service;

import cotato.backend.dto.request.ApplicantCreateRequest;
import cotato.backend.dto.request.ApplicantUpdateRequest;
import cotato.backend.dto.response.ApplicantResponse;
import cotato.backend.dto.response.ApplicantInfoResponse;
import cotato.backend.entity.Applicant;
import cotato.backend.entity.ApplicationDocument;
import cotato.backend.repository.ApplicantRepository;
import cotato.backend.repository.ApplicationDocumentRepository;
import cotato.backend.common.exception.AppException;
import cotato.backend.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApplicantService {

    private final ApplicantRepository applicantRepository;
    private final ApplicationDocumentRepository applicationDocumentRepository;

    @Transactional
    public ApplicantResponse createApplicant(ApplicantCreateRequest request) {
        Applicant applicant = applicantRepository.findByPhoneNumber(request.getPhoneNumber())
                .orElseGet(() -> applicantRepository.save(
                        Applicant.builder()
                                .name(request.getName())
                                .age(request.getAge())
                                .phoneNumber(request.getPhoneNumber())
                                .build()
                ));

        ApplicationDocument document = ApplicationDocument.builder()
                .applicant(applicant)
                .period(request.getPeriod())
                .part(request.getPart())
                .ability(request.getAbility())
                .passion(request.getPassion())
                .applicationTime(request.getApplicationTime())
                .build();

        ApplicationDocument savedDocument = applicationDocumentRepository.save(document);
        return ApplicantResponse.from(applicant, savedDocument);
    }

    public ApplicantInfoResponse getApplicant(Long applicantId) {
        Applicant applicant = applicantRepository.findById(applicantId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        return ApplicantInfoResponse.from(applicant);
    }

    @Transactional
    public ApplicantInfoResponse updateApplicant(Long applicantId, ApplicantUpdateRequest request) {
        Applicant applicant = applicantRepository.findById(applicantId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        
        applicant.update(request.getName(), request.getAge(), request.getPhoneNumber());
        return ApplicantInfoResponse.from(applicant);
    }
}
