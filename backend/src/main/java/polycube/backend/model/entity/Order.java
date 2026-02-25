package polycube.backend.model.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import polycube.backend.model.type.PaymentMethod;
import polycube.backend.payload.OrderRequest;
import polycube.backend.policy.DiscountPolicy;

import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String itemName;
    private int originalPrice;
    private int discountPrice;
    private PaymentMethod paymentMethod;

    public Order(Member member, OrderRequest request) {
        this.member = member;
        this.itemName = request.itemName();
        this.originalPrice = request.price();
        this.paymentMethod = request.paymentMethod();
    }

    public int calculateFinalPrice() {
        return originalPrice - discountPrice;
    }

    public void applyDiscounts(List<DiscountPolicy> policies) {
        int currentPrice = this.originalPrice;
        int totalDiscount = 0;

        for (DiscountPolicy policy : policies) {
            int discount = policy.calculateDiscount(currentPrice);
            totalDiscount += discount;
            currentPrice -= discount;
        }
        this.discountPrice = totalDiscount;
    }

    public Payment pay(PaymentMethod method) {
        int finalAmount = calculateFinalPrice();
        return new Payment(this, finalAmount, method, member.getGrade());
    }
}
