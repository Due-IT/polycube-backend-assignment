package polycube.backend.discount;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import polycube.backend.discount.policy.AppliedDiscountPolicy;
import polycube.backend.discount.policy.DiscountPolicy;
import polycube.backend.discount.setting.DiscountSetting;
import polycube.backend.discount.setting.DiscountSettingRepository;
import polycube.backend.model.entity.Order;

import java.util.Comparator;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DiscountPolicyProvider {

    private final List<DiscountPolicy> policies;
    private final DiscountSettingRepository discountSettingRepository;

    public List<AppliedDiscountPolicy> getPolicies(Order order) {
        DiscountContext discountContext = getDiscountContext(order);

        return policies.stream()
                .filter(policy -> policy.isSupport(discountContext))
                .map(policy -> {
                    DiscountSetting setting = discountSettingRepository.findByPolicyName(policy.getTarget())
                            .orElseThrow(() -> new IllegalStateException("설정 없음: " + policy.getTarget()));
                    return new AppliedDiscountPolicy(policy, setting);
                })
                .sorted(Comparator.comparingInt(AppliedDiscountPolicy::getPriority))
                .toList();
    }

    private static DiscountContext getDiscountContext(Order order) {
        return new DiscountContext(
                order.getMember().getGrade(),
                order.getPaymentMethod()
        );
    }
}
