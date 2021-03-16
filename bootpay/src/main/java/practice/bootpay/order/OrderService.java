package practice.bootpay.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.bootpay.item.Item;
import practice.bootpay.item.ItemRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;

    /**
     * 상품 생성
     * 테스트용으로 주문 폼 작성시 상품 생성
     */
    public Item createItem(OrderForm form) {
        Item item = Item.builder()
                .name(form.getItemName())
                .price(form.getPrice())
                .build();
        return itemRepository.save(item);
    }

    /**
     * 주문 생성
     * @param form 주문 폼
     */
    public Order createOrder(OrderForm form) {
        Item item = itemRepository.findByName(form.getItemName());
        Order order = Order.createOrder(item, form.getUsername());
        return orderRepository.save(order);
    }

    /**
     * 주문 삭제
     * @param id order id
     */
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    /**
     * 주문 실패 로그
     * @param id order id
     */
    @Transactional
    public void failOrder(Long id) {
        orderRepository.deleteById(id);
    }


    /**
     * 주문 정상 완료
     * @param id order id
     */
    @Transactional
    public void completeOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow();
        order.completeOrder();
    }

    /**
     * 주문 찾기
     */
    @Transactional(readOnly = true)
    public Order findOrder(Long id) {
        return orderRepository.findById(id).orElseThrow();
    }

}
