package practice.bootpay.order;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import practice.bootpay.item.Item;

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

    //주문당 한개의 상품 밖에 못산다고 가정 (test 용)
    @ManyToOne
    private Item item;
    private LocalDateTime createdDate;

    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;

    @Builder
    private Order(String username, Item item) {
        this.username = username;
        this.item = item;
        this.createdDate = LocalDateTime.now();
        this.orderStatus = OrderStatus.ORDER;
    }

    /**
     * 주문 생성
     * @return Order entity
     */
    public static Order createOrder(Item item, String username) {
        return Order.builder()
                .item(item)
                .username(username)
                .build();
    }

    /**
     * 주문 정상적으로 완료
     */
    public void completeOrder() {
        this.orderStatus = OrderStatus.COMP;
    }

    /**
     * 주문 실패
     */
    public void failOrder() {
        this.orderStatus = OrderStatus.FAIL;
    }
}
