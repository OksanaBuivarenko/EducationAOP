package t.education.aop.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import t.education.aop.dto.responce.ExecutionTimeRs;
import t.education.aop.entity.ExecutionTime;
import t.education.aop.dto.responce.StatisticRs;
import t.education.aop.repository.ExecutionTimeRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ExecutionTimeServiceTest {

    private final ExecutionTimeRepository executionTimeRepository = Mockito.mock(ExecutionTimeRepository.class);

    private final ExecutionTimeService executionTimeService = new ExecutionTimeService(executionTimeRepository);

    private ExecutionTime executionTime;

    private ExecutionTime executionTime2;

    private ExecutionTime executionTime3;

    private List<ExecutionTime> executionTimeList;

    @BeforeEach
    void setUp() {
        executionTime = ExecutionTime.builder()
                .id(1L)
                .methodName("getOrderList")
                .startTime(LocalDateTime.parse("2019-03-27T10:15:30"))
                .duration(349L)
                .build();

        executionTime2 = ExecutionTime.builder()
                .id(2L)
                .methodName("getOrderByOrderId")
                .startTime(LocalDateTime.parse("2019-04-28T19:25:00"))
                .duration(84L)
                .build();

        executionTime3 = ExecutionTime.builder()
                .id(3L)
                .methodName("getOrderByOrderId")
                .startTime(LocalDateTime.parse("2019-04-30T22:08:00"))
                .duration(65L)
                .build();

        executionTimeList = new ArrayList<>();
        executionTimeList.add(executionTime);
        executionTimeList.add(executionTime2);
        executionTimeList.add(executionTime3);
    }

    @AfterEach
    void tearDown() {
        executionTime = null;
        executionTime2 = null;
        executionTime3 = null;
        executionTimeList = null;
    }

    @Test
    @DisplayName("Create executionTime")
    void createExecutionTime() {
        executionTimeService.createExecutionTime("getOrderList", System.currentTimeMillis(), 349L);
        verify(executionTimeRepository, times(1)).save(any(ExecutionTime.class));
    }

    @Test
    @DisplayName("Get executionTime list")
    void getExecutionTimeList() {
        when(executionTimeRepository.findAll()).thenReturn(executionTimeList);
        List<ExecutionTimeRs> executionTimeRsList = executionTimeService.getExecutionTimeList();
        assertEquals(3, executionTimeRsList.size());
        verify(executionTimeRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Get statistic by all methods")
    void getStatisticByAllMethods() {
        when(executionTimeRepository.findFirstByOrderByDurationAsc()).thenReturn(executionTime3);
        when(executionTimeRepository.findFirstByOrderByDurationDesc()).thenReturn(executionTime);
        when(executionTimeRepository.findAll()).thenReturn(executionTimeList);
        StatisticRs statisticRs = executionTimeService.getStatisticByAllMethods();
        assertEquals(349, statisticRs.getMaxDuration());
        assertEquals(65, statisticRs.getMinDuration());
        assertEquals(207, statisticRs.getMiddleDuration());
        verify(executionTimeRepository, times(1)).findAll();
        verify(executionTimeRepository, times(1)).findFirstByOrderByDurationAsc();
        verify(executionTimeRepository, times(1)).findFirstByOrderByDurationDesc();
    }

    @Test
    @DisplayName("Get statistic by method name")
    void getStatisticByMethodName() {
        when(executionTimeRepository.findFirstByMethodNameOrderByDurationAsc("getOrderByOrderId")).
                thenReturn(Optional.ofNullable(executionTime3));
        when(executionTimeRepository.findFirstByMethodNameOrderByDurationDesc("getOrderByOrderId")).
                thenReturn(Optional.ofNullable(executionTime2));
        List<ExecutionTime> getOrderByOrderIdList = new ArrayList<>();
        getOrderByOrderIdList.add(executionTime3);
        getOrderByOrderIdList.add(executionTime2);
        when(executionTimeRepository.findAllByMethodName("getOrderByOrderId")).thenReturn(getOrderByOrderIdList);
        StatisticRs statisticRs = executionTimeService.getStatisticByMethodName("getOrderByOrderId");
        assertEquals(84, statisticRs.getMaxDuration());
        assertEquals(65, statisticRs.getMinDuration());
        assertEquals(74, statisticRs.getMiddleDuration());
        verify(executionTimeRepository, times(1)).findAllByMethodName("getOrderByOrderId");
        verify(executionTimeRepository, times(1)).
                findFirstByMethodNameOrderByDurationAsc("getOrderByOrderId");
        verify(executionTimeRepository, times(1)).
                findFirstByMethodNameOrderByDurationDesc("getOrderByOrderId");
    }

    @Test
    @DisplayName("Get statisticRs")
    void getStatisticRs() {
        StatisticRs statisticRs = executionTimeService.getStatisticRs(65L, 349L, executionTimeList);
        assertEquals(349, statisticRs.getMaxDuration());
        assertEquals(65, statisticRs.getMinDuration());
        assertEquals(207, statisticRs.getMiddleDuration());
    }

    @Test
    @DisplayName("Get annotate methods")
    void getAnnotateMethods() {
        List<String> methodNames = executionTimeService.
                getAnnotateMethods(t.education.aop.annotation.TrackAsyncTime.class);
        assertEquals(0, methodNames.size());
    }

    @Test
    @DisplayName("Find all classes using classLoader")
    void findAllClassesUsingClassLoader() {
        Set<Class> classSet = executionTimeService.findAllClassesUsingClassLoader("t.education.aop.service");
        assertEquals(3, classSet.size());
    }
}