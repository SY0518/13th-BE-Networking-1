package cotato.backend.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ApplicantPart {
    PLANNING("기획"),
    DESIGN("디자이너"),
    FRONTEND("프론트엔드"),
    BACKEND("백엔드");

    private final String description;
}
