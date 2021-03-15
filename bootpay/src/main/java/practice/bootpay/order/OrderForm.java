package practice.bootpay.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderForm {
    private String itemName;
    private String username;
    private int price;
}
