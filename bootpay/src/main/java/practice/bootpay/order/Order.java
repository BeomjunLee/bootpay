package practice.bootpay.order;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity @Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;
    private String username;
    private String itemName;
    private int price;
    private LocalDateTime createdDate;
    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;

    @Builder
    private Order(String username, String itemName, int price) {
        this.username = username;
        this.itemName = itemName;
        this.price = price;
        this.createdDate = LocalDateTime.now();
        this.orderStatus = OrderStatus.ORDER;
    }

    /**
     * 주문 생성
     * @param form 주문 폼
     * @return Order entity
     */
    public static Order createOrder(OrderForm form) {
        return Order.builder()
                .itemName(form.getItemName())
                .username(form.getUsername())
                .price(form.getPrice())
                .build();
    }

    /**
     * 주문 정상적으로 완료
     */
    public void completeOrder() {
        this.orderStatus = OrderStatus.COMP;
    }
}
