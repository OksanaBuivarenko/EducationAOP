package t.education.aop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import t.education.aop.entity.Tourist;

public interface TouristRepository extends JpaRepository<Tourist, Long> {
}
