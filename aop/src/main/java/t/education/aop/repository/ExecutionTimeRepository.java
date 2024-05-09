package t.education.aop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import t.education.aop.entity.ExecutionTime;

import java.util.List;
import java.util.Optional;

public interface ExecutionTimeRepository extends JpaRepository<ExecutionTime, Long> {

    List<ExecutionTime> findAllByMethodName(String methodName);

    ExecutionTime findFirstByOrderByDurationAsc();

    ExecutionTime findFirstByOrderByDurationDesc();

    Optional<ExecutionTime> findFirstByMethodNameOrderByDurationAsc(String methodName);

    Optional<ExecutionTime> findFirstByMethodNameOrderByDurationDesc(String methodName);
}
