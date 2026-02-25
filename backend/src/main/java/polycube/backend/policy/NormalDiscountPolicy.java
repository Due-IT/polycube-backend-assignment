package polycube.backend.policy;

import org.springframework.stereotype.Component;
import polycube.backend.model.type.Grade;

@Component
public class NormalDiscountPolicy implements DiscountPolicy {

    @Override
    public boolean isSupport(Grade grade) {
        return grade == Grade.NORMAL;
    }

    @Override
    public int calculateDiscount(Grade grade, int price) {
        return 0;
    }
}
