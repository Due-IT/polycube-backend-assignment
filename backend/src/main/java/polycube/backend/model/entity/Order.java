package polycube.backend.model.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import polycube.backend.model.type.PaymentMethod;
import polycube.backend.policy.DiscountPolicy;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // 실무 필수: 지연 로딩
    @JoinColumn(name = "member_id")
    private Member member;

    private String itemName;
    private int originalPrice;
    private int discountPrice;

    public Order(Member member, String itemName, int originalPrice) {
        this.member = member;
        this.itemName = itemName;
        this.originalPrice = originalPrice;
    }

    public int calculateFinalPrice() {
        return originalPrice - discountPrice;
    }

    public void applyDiscount(DiscountPolicy policy) {
        this.discountPrice = policy.calculateDiscount(member.getGrade(), originalPrice);
    }

    public Payment pay(PaymentMethod method) {
        int finalAmount = calculateFinalPrice();
        return new Payment(this, finalAmount, method, member.getGrade());
    }
}
