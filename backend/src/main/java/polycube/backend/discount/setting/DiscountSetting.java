package polycube.backend.discount.setting;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "discount_settings")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiscountSetting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String policyName;
    private int discountPercent;
    private int discountAmount;
    private int priority;

    public DiscountSetting(String policyName, int discountPercent, int discountAmount, int priority) {
        this.policyName = policyName;
        this.discountPercent = discountPercent;
        this.discountAmount = discountAmount;
        this.priority = priority;
    }

    public void update(String name, int discountPercent, int discountAmount, int priority) {
        this.policyName = policyName;
        this.discountPercent = discountPercent;
        this.discountAmount = discountAmount;
        this.priority = priority;
    }
}
