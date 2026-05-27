package cotato.backend.service;

import cotato.backend.common.exception.AppException;
import cotato.backend.common.exception.ErrorCode;
import cotato.backend.dto.request.DocumentListRequest;
import cotato.backend.dto.response.ApplicantResponse;
import cotato.backend.dto.response.DocumentListResponse;
import cotato.backend.entity.ApplicationDocument;
import cotato.backend.entity.Like;
import cotato.backend.entity.Operator;
import cotato.backend.repository.ApplicationDocumentRepository;
import cotato.backend.repository.LikeRepository;
import cotato.backend.repository.OperatorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApplicationDocumentService {

    private final ApplicationDocumentRepository applicationDocumentRepository;
    private final OperatorRepository operatorRepository;
    private final LikeRepository likeRepository;

    public ApplicantResponse getDocument(Long documentId) {
        ApplicationDocument document = applicationDocumentRepository.findById(documentId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        return ApplicantResponse.from(document.getApplicant(), document);
    }

    public List<DocumentListResponse> listDocuments(DocumentListRequest request) {
        Sort sort = Sort.unsorted();
        if ("likes".equals(request.getFilterBy())) {
            sort = Sort.by(Sort.Direction.DESC, "likesCount");
        } else if ("gisu".equals(request.getFilterBy())) {
            sort = Sort.by(Sort.Direction.ASC, "period");
        } else if ("gisu+likes".equals(request.getFilterBy())) {
            sort = Sort.by(Sort.Direction.ASC, "period").and(Sort.by(Sort.Direction.DESC, "likesCount"));
        }

        Pageable pageable = PageRequest.of(request.getPage(), request.getPageSize(), sort);
        Page<ApplicationDocument> result = applicationDocumentRepository.findAll(pageable);

        return result.getContent().stream()
                .map(DocumentListResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public void toggleLike(Long documentId, Long operatorId) {
        ApplicationDocument document = applicationDocumentRepository.findById(documentId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        Operator operator = operatorRepository.findById(operatorId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));

        likeRepository.findByDocumentAndOperator(document, operator)
                .ifPresentOrElse(
                        like -> {
                            document.removeLike(like);
                            likeRepository.delete(like);
                        },
                        () -> {
                            Like like = Like.builder()
                                    .document(document)
                                    .operator(operator)
                                    .build();
                            document.addLike(like);
                            likeRepository.save(like);
                        }
                );
    }
}
