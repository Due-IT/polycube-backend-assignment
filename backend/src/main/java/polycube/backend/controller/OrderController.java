package polycube.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import polycube.backend.model.entity.Order;
import polycube.backend.payload.OrderRequest;
import polycube.backend.payload.OrderResponse;
import polycube.backend.service.OrderService;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> order(@Valid @RequestBody OrderRequest request) {
        Order result = orderService.createOrder(request);

        OrderResponse response = new OrderResponse(
                "SUCCESS",
                "주문이 성공적으로 완료되었습니다.",
                result.getFinalPrice(),
                result.getPaymentMethod(),
                result.getPaidAt()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
