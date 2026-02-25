package polycube.backend.policy;

import org.springframework.stereotype.Component;
import polycube.backend.model.type.Grade;

@Component
public class NormalDiscountPolicy implements DiscountPolicy {

    @Override
    public int calculateDiscount(Grade grade, int price) {
        // 일반 등급은 할인이 없으므로 항상 0을 반환
        // 만약 실수로 다른 등급이 이 정책을 타더라도 0원을 반환하여 시스템의 정합성을 유지함
        return 0;
    }
}
