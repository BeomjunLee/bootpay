package practice.bootpay.order;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderServiceTest {

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @BeforeEach
    void init() {
        OrderForm form = OrderForm.builder()
                .itemName("책")
                .username("이범준")
                .price(10000)
                .build();
        orderService.createdOrder(form);
    }

    @Test
    void createOrder() {
        Order order = orderRepository.findById(1L).get();

        Assertions.assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.ORDER);
        Assertions.assertThat(order.getItemName()).isEqualTo("책");
        Assertions.assertThat(order.getUsername()).isEqualTo("이범준");
        Assertions.assertThat(order.getPrice()).isEqualTo(10000);
    }

}