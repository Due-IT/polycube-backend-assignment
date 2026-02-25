package polycube.backend.policy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import polycube.backend.model.type.Grade;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DiscountPolicyProvider {

    private final List<DiscountPolicy> policies;

    public DiscountPolicy getPolicy(Grade grade) {
        return policies.stream()
                .filter(policy -> policy.isSupport(grade))
                .findFirst()
                .orElse(new NormalDiscountPolicy()); // 없으면 기본 정책
    }
}
