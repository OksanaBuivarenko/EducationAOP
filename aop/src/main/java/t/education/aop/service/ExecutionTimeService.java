package t.education.aop.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import t.education.aop.dto.responce.ExecutionTimeRs;
import t.education.aop.exception.MethodNotFoundException;
import t.education.aop.exception.ObjectNotFoundException;
import t.education.aop.mapper.ExecutionTimeMapper;
import t.education.aop.dto.responce.StatisticRs;
import t.education.aop.entity.ExecutionTime;
import t.education.aop.repository.ExecutionTimeRepository;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExecutionTimeService {

    private final ExecutionTimeRepository executionTimeRepository;

    @Async
    public Future<ExecutionTime> createExecutionTime(String methodName, Long startTime, Long duration) {
        ExecutionTime executionTime = ExecutionTime.builder()
                .methodName(methodName)
                .startTime(LocalDateTime.ofInstant(Instant.ofEpochMilli(startTime),
                        TimeZone.getDefault().toZoneId()))
                .duration(duration)
                .build();
        executionTimeRepository.save(executionTime);
        return new AsyncResult<>(executionTime);
    }

    public List<ExecutionTimeRs> getExecutionTimeList() {
        return executionTimeRepository.findAll().stream().map(ExecutionTimeMapper.INSTANCE::toDto).toList();
    }

    public StatisticRs getStatisticByAllMethods() {
        try {
            Long min = executionTimeRepository.findFirstByOrderByDurationAsc().getDuration();
            Long max = executionTimeRepository.findFirstByOrderByDurationDesc().getDuration();
            List<ExecutionTime> executionTimes = executionTimeRepository.findAll();
            return getStatisticRs(min, max, executionTimes);
        }
        catch (Exception e) {
            return getStatisticRs(0L, 0L, new ArrayList<>());
        }
    }

    public StatisticRs getStatisticByMethodName(String methodName) {
        Long min = executionTimeRepository.findFirstByMethodNameOrderByDurationAsc(methodName)
                .orElseThrow(() -> new MethodNotFoundException(methodName)).getDuration();
        Long max = executionTimeRepository.findFirstByMethodNameOrderByDurationDesc(methodName)
                .orElseThrow(() -> new MethodNotFoundException(methodName)).getDuration();
        List<ExecutionTime> executionTimes = executionTimeRepository.findAllByMethodName(methodName);
        return getStatisticRs(min, max, executionTimes);
    }

    public StatisticRs getStatisticRs(Long min, Long max, List<ExecutionTime> executionTimes) {
        return StatisticRs.builder()
                .minDuration(min)
                .maxDuration(max)
                .middleDuration((min + max) / 2)
                .countMethodExecutions((long) executionTimes.size())
                .sumDuration(executionTimes.stream().mapToLong(ExecutionTime::getDuration).sum())
                .build();
    }

    public List<StatisticRs> getStatisticByAnnotateMethods(Class annotationClass) {
        List<StatisticRs> statisticRsList = new ArrayList<>();
        for (String methodName : getAnnotateMethods(annotationClass)) {
            if (!executionTimeRepository.findAllByMethodName(methodName).isEmpty()) {
                statisticRsList.add(getStatisticByMethodName(methodName));
            }
        }
        return statisticRsList;
    }

    public List<String> getAnnotateMethods(Class annotationClass) {
        List<String> methodNames = new ArrayList<>();
        for (Class myClassClass : findAllClassesUsingClassLoader("t.education.aop.service")) {
            Method[] methods = myClassClass.getMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(annotationClass)) {
                    methodNames.add(method.getName());
                }
            }
        }
        return methodNames;
    }

    public Set<Class> findAllClassesUsingClassLoader(String packageName) {
        InputStream stream = ClassLoader.getSystemClassLoader()
                .getResourceAsStream(packageName.replaceAll("[.]", "/"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        return reader.lines()
                .filter(line -> line.endsWith(".class"))
                .map(line -> getClass(line, packageName))
                .collect(Collectors.toSet());
    }

    @SneakyThrows
    private Class getClass(String className, String packageName) {
        return Class.forName(packageName + "."
                + className.substring(0, className.lastIndexOf('.')));
    }
}
