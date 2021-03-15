package practice.bootpay.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @PostMapping("/pay")
    public String pay(OrderForm form, Model model) {
        Order order = orderService.createdOrder(form);

        model.addAttribute("order", order);
        return "pay";
    }

    @GetMapping("/pay/delete")
    public String deletePay(@RequestParam("id") Long id) {
        orderService.deleteOrder(id);
        return "redirect:/";
    }

    @GetMapping("/pay/complete")
    public String completePay(@RequestParam("id") Long id) {
        orderService.completeOrder(id);
        return "redirect:/";
    }
}
