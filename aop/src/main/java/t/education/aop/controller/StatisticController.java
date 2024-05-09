package t.education.aop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import t.education.aop.dto.responce.ExecutionTimeRs;
import t.education.aop.dto.responce.StatisticRs;
import t.education.aop.service.ExecutionTimeService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/statistic")
public class StatisticController {

    private final ExecutionTimeService executionTimeService;

    @GetMapping("/")
    public List<ExecutionTimeRs> getOrdersExecutionTime() {
        return executionTimeService.getExecutionTimeList();
    }

    @GetMapping("/all_methods")
    public StatisticRs getStatisticByAllMethods() {
        return executionTimeService.getStatisticByAllMethods();
    }

    @GetMapping("/sync")
    public List<StatisticRs> getStatisticBySyncMethods() {
        return executionTimeService.getStatisticByAnnotateMethods(t.education.aop.annotation.TrackTime.class);
    }

    @GetMapping("/async")
    public List<StatisticRs> getStatisticByAsyncMethods() {
        return executionTimeService.getStatisticByAnnotateMethods(t.education.aop.annotation.TrackAsyncTime.class);
    }

    @GetMapping("/method_name/{methodName}")
    public StatisticRs getStatisticByMethodName(@PathVariable String methodName) {
        return executionTimeService.getStatisticByMethodName(methodName);
    }
}
