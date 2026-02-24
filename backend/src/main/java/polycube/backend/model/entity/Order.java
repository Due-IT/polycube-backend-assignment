package polycube.backend.model.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import polycube.backend.model.type.PaymentMethod;

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
        int discountAmount = switch (member.getGrade()) {
            case VIP -> 1000;
            case VVIP -> {
                int discountRateAmount = (int) (price * 0.1);
                // 할인액이 1000원 미만이면 1000원 할인
                yield Math.max(discountRateAmount, 1000);
            }
            case NORMAL -> 0;
        };

        // 할인액이 상품 원가보다 크면 원가만큼만 할인
        return Math.min(discountAmount, price);
    }

    public int calculateFinalPrice() {
        return originalPrice - discountPrice;
    }

    public Payment pay(PaymentMethod method) {
        int finalAmount = calculateFinalPrice();
        return new Payment(this, finalAmount, method, member.getGrade());
    }
}
