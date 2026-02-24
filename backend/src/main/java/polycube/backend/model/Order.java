package polycube.backend.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
        this.discountPrice = calculateDiscount(member, originalPrice);
    }

    private int calculateDiscount(Member member, int price) {
        return switch (member.getGrade()) {
            case VIP -> 1000;
            case VVIP -> {
                int discountRateAmount = (int) (price * 0.1);
                // 10% 할인액이 1,000원보다 작으면 1,000원을 선택 (최소 할인 보장)
                yield Math.max(discountRateAmount, 1000);
            }
            case NORMAL -> 0;
        };
    }

    public int calculateFinalPrice() {
        return originalPrice - discountPrice;
    }

    public Payment pay(PaymentMethod method) {
        int finalAmount = calculateFinalPrice();
        return new Payment(this, finalAmount, method);
    }
}
