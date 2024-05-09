package t.education.aop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.ResultMatcher;
import t.education.aop.config.TestConfig;
import t.education.aop.dto.request.OrderRq;
import t.education.aop.dto.responce.TourOrderRs;
import t.education.aop.entity.TourOrder;
import t.education.aop.service.TourOrderService;

import java.util.Date;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("/sql/insert.sql")
@Sql(scripts = "/sql/clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestConfig.class)
@AutoConfigureMockMvc
class OrderControllerTest {
    @LocalServerPort
    private Integer port;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    TourOrderService tourOrderService;

    @DisplayName("Get orders test with status 200")
    @Test
    void getOrders() throws Exception {
        this.mockMvc.perform(get("http://localhost:" + port + "/order/"))
                .andDo(print())
                .andExpectAll(status().isOk())
                .andExpect(jsonPath("$.length()").value(4));
    }

    @DisplayName("Get order by id with status 200")
    @Test
    void getOrderById() throws Exception {
        this.mockMvc.perform(get("http://localhost:" + port + "/order/1"))
                .andDo(print())
                .andExpectAll(status().isOk())
                .andExpect(jsonPath("$.country").value("Turkey"))
                .andExpect(jsonPath("$.city").value("Kemer"))
                .andExpect(jsonPath("$.tourist.firstName").value("Name1"));
    }

    @DisplayName("Get order by id with status 404")
    @Test
    void getOrderByIdWithErrorId() throws Exception {
        this.mockMvc.perform(get("http://localhost:" + port + "/order/100"))
                .andDo(print())
                .andExpectAll(status().is4xxClientError());
    }

    @DisplayName("Create order")
    @Test
    void addOrder() throws Exception {
        OrderRq orderRq = OrderRq.builder()
                .country("Russia").city("Moscow")
                .checkInDate(new Date()).restDuration(10)
                .hotel("Park Inn by Radisson").mealsType("BB")
                .firstName("Test").lastName("Test")
                .phone("345345").email("test@test.com")
                .build();
        ObjectMapper mapper = new ObjectMapper();
        this.mockMvc.perform(post("http://localhost:" + port + "/order/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(orderRq)))
                .andDo(print())
                .andExpectAll(status().isOk());
    }

    @DisplayName("Get tourist by id with status 200")
    @Test
    void getTouristByTouristId() throws Exception {
        this.mockMvc.perform(get("http://localhost:" + port + "/order/tourist/2"))
                .andDo(print())
                .andExpect(jsonPath("$.firstName").value("Name2"))
                .andExpect(jsonPath("$.email").value("2@2.com"))
                .andExpectAll(status().isOk());
    }

    @DisplayName("Get tourist by id with status 404")
    @Test
    void getTouristByTouristIdWithErrorId() throws Exception {
        this.mockMvc.perform(get("http://localhost:" + port + "/order/tourist/200"))
                .andDo(print())
                .andExpectAll(status().is4xxClientError());
    }
}