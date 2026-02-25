package polycube.backend.policy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import polycube.backend.model.entity.Order;

import java.util.Comparator;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DiscountPolicyProvider {

    private final List<DiscountPolicy> policies;

    public List<DiscountPolicy> getPolicies(Order order) {
        DiscountContext discountContext = getDiscountContext(order);

        List<DiscountPolicy> matchedPolicies = policies.stream()
                .filter(policy -> policy.isSupport(discountContext))
                .sorted(Comparator.comparingInt(DiscountPolicy::getPriority))
                .toList();

        if (matchedPolicies.isEmpty()) {
            return List.of(new NormalDiscountPolicy());
        }

        return matchedPolicies;
    }

    private static DiscountContext getDiscountContext(Order order) {
        return new DiscountContext(
                order.getMember().getGrade(),
                order.getPaymentMethod()
        );
    }
}
