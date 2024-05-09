package t.education.aop.controller;

import jdk.jfr.Description;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;
import t.education.aop.dto.request.OrderRq;
import t.education.aop.dto.responce.TourOrderRs;
import t.education.aop.dto.responce.TouristRs;
import t.education.aop.mapper.OrderMapper;
import t.education.aop.mapper.TouristMapper;
import t.education.aop.entity.TourOrder;
import t.education.aop.entity.Tourist;
import t.education.aop.service.TourOrderService;
import t.education.aop.service.TouristService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final TourOrderService orderService;
    private final TouristService touristService;

    @GetMapping("/")
    public List<TourOrderRs> getOrders() {
        return orderService.getOrderList();
    }

    @SneakyThrows
    @GetMapping("/{orderId}")
    public TourOrderRs getOrderById(@PathVariable Long orderId) {
        return orderService.getOrderByOrderId(orderId).get();
    }

    @PostMapping("/")
    public TourOrderRs addOrder(@RequestBody OrderRq orderRq) {
        Tourist tourist = touristService.addTourist(TouristMapper.INSTANCE.toEntity(orderRq));
        TourOrder tourOrder = OrderMapper.INSTANCE.toEntity(orderRq);
        tourOrder.setTourist(tourist);
        return orderService.addOrder(tourOrder);
    }

    @GetMapping("/tourist/{touristId}")
    public TouristRs getTouristByTouristId(@PathVariable Long touristId) {
        return touristService.getTouristById(touristId);
    }

}
