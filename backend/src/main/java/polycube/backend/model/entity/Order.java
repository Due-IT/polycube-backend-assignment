package polycube.backend.model.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import polycube.backend.discount.history.DiscountHistory;
import polycube.backend.discount.policy.AppliedDiscountPolicy;
import polycube.backend.model.type.Grade;
import polycube.backend.model.type.PaymentMethod;
import polycube.backend.payload.OrderRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DiscountHistory> discountHistories = new ArrayList<>();

    private String itemName;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private Grade paidGrade;

    private int originalPrice;
    private int discountPrice;
    private int finalPrice;
    private LocalDateTime paidAt;

    public Order(Member member, OrderRequest request) {
        this.member = member;
        this.itemName = request.itemName();
        this.originalPrice = request.price();
        this.paymentMethod = request.paymentMethod();
        this.paidGrade = member.getGrade();
    }

    public void applyDiscounts(List<AppliedDiscountPolicy> policies) {
        int currentPrice = this.originalPrice;
        int totalDiscount = 0;

        for (AppliedDiscountPolicy policy : policies) {
            int discount = policy.calculateDiscount(currentPrice);
            totalDiscount += discount;
            currentPrice -= discount;

            this.discountHistories.add(new DiscountHistory(this.member, this, policy, discount));
        }
        this.discountPrice = totalDiscount;
    }

    public void pay() {
        this.finalPrice = calculateFinalPrice();
        this.paidAt = LocalDateTime.now();
    }

    private int calculateFinalPrice() {
        return originalPrice - discountPrice;
    }
}
