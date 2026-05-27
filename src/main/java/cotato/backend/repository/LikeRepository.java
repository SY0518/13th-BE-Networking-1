package cotato.backend.repository;

import cotato.backend.entity.Like;
import cotato.backend.entity.ApplicationDocument;
import cotato.backend.entity.Operator;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByDocumentAndOperator(ApplicationDocument document, Operator operator);
}
