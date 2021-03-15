package practice.bootpay.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/")
    public String home() {
        return "/home";
    }

    @PostMapping("/pay")
    public String pay(OrderForm form, Model model) {
        orderService.createdOrder(form);
        return "/pay";
    }

}
