package polycube.backend.discount.history;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import polycube.backend.discount.policy.AppliedDiscountPolicy;
import polycube.backend.model.entity.Member;
import polycube.backend.model.entity.Order;
import polycube.backend.model.type.Grade;

@Entity
@Table(name = "discount_histories")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiscountHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Enumerated(EnumType.STRING)
    private Grade appliedGrade;

    private String policyName;
    private int policyDiscountPercent;
    private int policyDiscountAmount;
    private int appliedDiscountAmount;

    public DiscountHistory(Member member, Order order, AppliedDiscountPolicy applied, int discount) {
        this.order = order;
        this.policyName = applied.setting().getPolicyName();
        this.appliedGrade = member.getGrade();
        this.policyDiscountPercent = applied.setting().getDiscountPercent();
        this.policyDiscountAmount = applied.setting().getDiscountAmount();
        this.appliedDiscountAmount = discount;
    }
}
