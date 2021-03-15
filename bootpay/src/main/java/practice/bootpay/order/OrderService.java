package practice.bootpay.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    /**
     * 주문 생성
     * @param form 주문 폼
     */
    public void createdOrder(OrderForm form) {
        Order order = Order.builder()
                .itemName(form.getItemName())
                .username(form.getUsername())
                .price(form.getPrice())
                .createdDate(LocalDateTime.now())
                .orderStatus(OrderStatus.ORDER)
                .build();

        orderRepository.save(order);
    }
}
