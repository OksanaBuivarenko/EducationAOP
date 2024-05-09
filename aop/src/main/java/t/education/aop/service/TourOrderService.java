package t.education.aop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import t.education.aop.annotation.TrackAsyncTime;
import t.education.aop.annotation.TrackTime;
import t.education.aop.dto.responce.TourOrderRs;
import t.education.aop.exception.ObjectNotFoundException;
import t.education.aop.entity.TourOrder;
import t.education.aop.mapper.OrderMapper;
import t.education.aop.repository.TourOrderRepository;

import java.util.List;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TourOrderService {
    private final TourOrderRepository orderRepository;

    @Async
    @TrackAsyncTime
    public Future<TourOrderRs> getOrderByOrderId(Long orderId) {
        TourOrder tourOrder = orderRepository.findById(orderId).orElseThrow(() -> new ObjectNotFoundException(orderId));
        return new AsyncResult(OrderMapper.INSTANCE.toDto(tourOrder));
    }

    @TrackTime
    public List<TourOrderRs> getOrderList() {
        return orderRepository.findAll().stream().map(OrderMapper.INSTANCE::toDto).collect(Collectors.toList());
    }

    @TrackTime
    public TourOrderRs addOrder(TourOrder tourOrder) {
        return OrderMapper.INSTANCE.toDto(orderRepository.save(tourOrder));
    }
}
