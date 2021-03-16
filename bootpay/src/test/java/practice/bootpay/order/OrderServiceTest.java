package practice.bootpay.order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

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
        orderService.createItem(form);
        orderService.createOrder(form);
    }

    @Test
    void createOrder() {
        Order order = orderRepository.findById(2L).get();

        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.ORDER);
        assertThat(order.getItem().getName()).isEqualTo("책");
        assertThat(order.getUsername()).isEqualTo("이범준");
        assertThat(order.getItem().getPrice()).isEqualTo(10000);
    }

    @Test
    void completeOrder() {
        Order order = orderRepository.findById(2L).get();
        order.completeOrder();

        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.COMP);
    }

}