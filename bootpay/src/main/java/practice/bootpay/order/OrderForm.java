package practice.bootpay.order;

import lombok.*;


@Data
public class OrderForm {
    private String itemName;
    private String username;
    private int price;

    @Builder
    public OrderForm(String itemName, String username, int price) {
        this.itemName = itemName;
        this.username = username;
        this.price = price;
    }
}
