package polycube.backend.policy;

import polycube.backend.model.type.Grade;

public interface DiscountPolicy {
    int calculateDiscount(Grade grade, int price);
}
