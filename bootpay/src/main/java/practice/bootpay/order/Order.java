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
    public Order(String username, String itemName, int price, LocalDateTime createdDate, OrderStatus orderStatus) {
        this.username = username;
        this.itemName = itemName;
        this.price = price;
        this.createdDate = createdDate;
        this.orderStatus = orderStatus;
    }
}
