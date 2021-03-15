package practice.bootpay.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    /**
     * 주문 생성
     * @param form 주문 폼
     */
    public Order createdOrder(OrderForm form) {
        Order order = Order.createOrder(form);
        return orderRepository.save(order);
    }

    /**
     * 주문 삭제
     * @param id order id
     */
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    /**
     * 주문 정상 완료
     * @param id order id
     */
    public void completeOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow();
        order.completeOrder();
    }

}
