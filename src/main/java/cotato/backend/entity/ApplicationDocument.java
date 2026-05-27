package cotato.backend.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "application_document")
public class ApplicationDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "document_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "applicant_id", nullable = false)
    private Applicant applicant;

    @Column(nullable = false)
    private Integer period;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicantPart part;

    @Column(nullable = false)
    private Integer ability;

    @Column(nullable = false)
    private Integer passion;

    @Column(nullable = false)
    private LocalDateTime applicationTime;

    @Column(nullable = false)
    private Integer likesCount = 0;

    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes = new ArrayList<>();

    @Builder
    public ApplicationDocument(Applicant applicant, Integer period, ApplicantPart part, Integer ability, Integer passion, LocalDateTime applicationTime) {
        this.applicant = applicant;
        this.period = period;
        this.part = part;
        this.ability = ability;
        this.passion = passion;
        this.applicationTime = applicationTime;
        this.likesCount = 0;
    }

    public void addLike(Like like) {
        this.likes.add(like);
        this.likesCount++;
    }

    public void removeLike(Like like) {
        this.likes.remove(like);
        if (this.likesCount > 0) {
            this.likesCount--;
        }
    }
}
