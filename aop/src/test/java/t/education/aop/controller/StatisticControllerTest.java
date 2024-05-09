package t.education.aop.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import t.education.aop.config.TestConfig;
import t.education.aop.service.ExecutionTimeService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("/sql/insert.sql")
@Sql(scripts = "/sql/clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestConfig.class)
@AutoConfigureMockMvc
class StatisticControllerTest {

    @LocalServerPort
    private Integer port;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ExecutionTimeService executionTimeService;

    @DisplayName("Get orders ExecutionTime")
    @Test
    void getOrdersExecutionTime() throws Exception {
        this.mockMvc.perform(get("http://localhost:" + port + "/statistic/"))
                .andDo(print())
                .andExpectAll(status().isOk())
                .andExpect(jsonPath("$.length()").value(3));
    }

    @DisplayName("Get statistic by all methods")
    @Test
    void getStatisticByAllMethods() throws Exception {
        this.mockMvc.perform(get("http://localhost:" + port + "/statistic/all_methods"))
                .andDo(print())
                .andExpect(jsonPath("$.minDuration").value(23))
                .andExpect(jsonPath("$.maxDuration").value(98))
                .andExpect(jsonPath("$.middleDuration").value(60))
                .andExpect(jsonPath("$.sumDuration").value(197))
                .andExpect(jsonPath("$.countMethodExecutions").value(3))
                .andExpectAll(status().isOk());
    }

    @DisplayName("Get statistic by method name")
    @Test
    void getStatisticByMethodName() throws Exception {
        this.mockMvc.perform(get("http://localhost:" + port + "/statistic/method_name/getOrderList"))
                .andDo(print())
                .andExpect(jsonPath("$.minDuration").value(76))
                .andExpect(jsonPath("$.maxDuration").value(76))
                .andExpect(jsonPath("$.middleDuration").value(76))
                .andExpect(jsonPath("$.sumDuration").value(76))
                .andExpect(jsonPath("$.countMethodExecutions").value(1))
                .andExpectAll(status().isOk());
    }

    @DisplayName("Get statistic by method name with error")
    @Test
    void getStatisticByMethodNameWithError() throws Exception {
        this.mockMvc.perform(get("http://localhost:" + port + "/statistic/method_name/getBook"))
                .andDo(print())
                .andExpectAll(status().is4xxClientError());
    }
}