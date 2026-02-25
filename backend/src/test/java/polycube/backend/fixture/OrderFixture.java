package polycube.backend.fixture;

import polycube.backend.model.entity.Member;
import polycube.backend.model.entity.Order;
import polycube.backend.model.type.PaymentMethod;
import polycube.backend.payload.OrderRequest;

public class OrderFixture {
    public static Order createOrder(Member member) {
        return createOrder(member, "테스트 상품", 10000, PaymentMethod.CREDIT_CARD);
    }

    public static Order createOrder(Member member, int price) {
        return createOrder(member, "테스트 상품", price, PaymentMethod.CREDIT_CARD);
    }

    public static Order createOrder(Member member, int price, PaymentMethod paymentMethod) {
        return createOrder(member, "테스트 상품", price, paymentMethod);
    }

    private static Order createOrder(Member member, String itemName, int price, PaymentMethod method) {
        OrderRequest request = new OrderRequest(
                member.getId(),
                itemName,
                price,
                method
        );
        return new Order(member, request);
    }
}
