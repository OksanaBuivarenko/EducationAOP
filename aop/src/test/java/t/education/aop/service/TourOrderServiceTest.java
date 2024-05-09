package t.education.aop.service;

import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import t.education.aop.dto.responce.TourOrderRs;
import t.education.aop.dto.responce.TouristRs;
import t.education.aop.entity.TourOrder;
import t.education.aop.entity.Tourist;
import t.education.aop.mapper.OrderMapper;
import t.education.aop.repository.TourOrderRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TourOrderServiceTest {

    private final TourOrderRepository orderRepository = Mockito.mock(TourOrderRepository.class);

    private final TourOrderService tourOrderService = new TourOrderService(orderRepository);

    private TourOrder tourOrder;

    @BeforeEach
    void setUp() {
        tourOrder = new TourOrder();
        tourOrder.setId(1L);
        tourOrder.setCountry("Turkey");
        tourOrder.setCity("Kemer");
    }

    @AfterEach
    void tearDown() {
        tourOrder = null;
    }

    @SneakyThrows
    @Test
    @DisplayName("Get order by id")
    void getOrderByOrderId() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(tourOrder));
        TourOrderRs tourOrderRs =  tourOrderService.getOrderByOrderId(1L).get();
        assertEquals("Kemer", tourOrderRs.getCity());
        assertEquals("Turkey", tourOrderRs.getCountry());
        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Get order list")
    void getOrderList() {
        List<TourOrder> tourOrderList = new ArrayList<>();
        tourOrderList.add(tourOrder);
        when(orderRepository.findAll()).thenReturn(tourOrderList);
        List<TourOrderRs> tourOrderRsList = tourOrderService.getOrderList();
        assertEquals(1, tourOrderRsList.size());
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Add order")
    void addOrder() {
        tourOrderService.addOrder(tourOrder);
        verify(orderRepository, times(1)).save(any(TourOrder.class));
    }
}