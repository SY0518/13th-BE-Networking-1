package cotato.backend.repository;

import cotato.backend.entity.ApplicationDocument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationDocumentRepository extends JpaRepository<ApplicationDocument, Long> {
}
