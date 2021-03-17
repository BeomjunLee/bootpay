package practice.bootpay.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import practice.bootpay.BootpayApi;
import practice.bootpay.dto.BootPayOrderDto;
import practice.bootpay.item.Item;
import practice.bootpay.model.request.Cancel;

import java.util.HashMap;
import java.util.Map;

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
        Item item = orderService.createItem(form);
        Order order = orderService.createOrder(form);
        model.addAttribute("order", order);
        model.addAttribute("item", item);
        return "pay";
    }

    @GetMapping("/pay/complete")
    public String completePay(@RequestParam("id") Long id) {
        orderService.completeOrder(id);
        return "redirect:/";
    }

    @GetMapping("/pay/delete")
    public String deletePay(@RequestParam("id") Long id) {
        orderService.deleteOrder(id);
        return "redirect:/";
    }

    /**
     * 결제 검증
     * @param
     * @return
     */
    @GetMapping("/pay/confirm")
    public ResponseEntity confirmPay(
                             @RequestParam("receipt_id") String receipt_id) throws Exception {
        String getDataJson = "";
        BootPayOrderDto dto = null;
        String rest_application_id = "rest_application_id";
        String private_key = "private_key";

        BootpayApi api = new BootpayApi(
                rest_application_id,
                private_key
        );
        api.getAccessToken();
        try {
            HttpResponse res = api.verify(receipt_id);
            getDataJson = IOUtils.toString(res.getEntity().getContent(), "UTF-8");
            System.out.println(getDataJson);

            ObjectMapper objectMapper = new ObjectMapper();
            dto = objectMapper.readValue(getDataJson, BootPayOrderDto.class);
            System.out.println(dto);

        } catch (Exception e) {
            e.printStackTrace();
        }

        long orderId = Long.parseLong(dto.getData().getOrder_id());
        Order order = orderService.findOrder(orderId);
        int price = order.getItem().getPrice();

        if (dto.getStatus() == 200) {

            //status가 1이고
            if (dto.getData().getPrice() == price && dto.getData().getStatus() == 1) {
                //결제 완료
                orderService.completeOrder(orderId);
                return ResponseEntity.ok("결제완료");
            }
        }

        //서버 검증 오류시
        Cancel cancel = new Cancel();
        cancel.receipt_id = receipt_id;
        cancel.name = "관리자";
        cancel.reason = "서버 검증 오류";

        //결제 오류 로그
        orderService.failOrder(orderId);
        String cancelDataJson = "";
        try {
            HttpResponse res = api.cancel(cancel);
            cancelDataJson = IOUtils.toString(res.getEntity().getContent(), "UTF-8");
            System.out.println(cancelDataJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().body("결제실패");
    }
}
