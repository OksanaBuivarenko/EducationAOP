package t.education.aop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import t.education.aop.entity.TourOrder;

public interface TourOrderRepository extends JpaRepository<TourOrder, Long> {

}
