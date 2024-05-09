package t.education.aop.aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import t.education.aop.service.ExecutionTimeService;

@Aspect
@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "myapp.feature.enabled", havingValue = "true")
public class TrackTimeAspect {
    private final ExecutionTimeService executionTimeService;

    @Pointcut("@annotation(t.education.aop.annotation.TrackAsyncTime) || @annotation(t.education.aop.annotation.TrackTime)")
    private void trackAllTime() {
    }

    @Around("trackAllTime()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        long startTime = System.currentTimeMillis();

        String methodName = proceedingJoinPoint.getSignature().getName();

        log.info("Старт выполнения метода {}", methodName);

        Object result = proceedingJoinPoint.proceed();

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        log.info("Метод {} выполнился за {} мс", methodName, duration);

        executionTimeService.createExecutionTime(methodName, startTime, duration);
        return result;
    }
}
