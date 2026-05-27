package cotato.backend.repository;

import cotato.backend.entity.Operator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperatorRepository extends JpaRepository<Operator, Long> {
}
