package polycube.backend.policy;

import polycube.backend.model.type.Grade;

public interface DiscountPolicy {
    boolean isSupport(Grade grade);

    int calculateDiscount(Grade grade, int price);
}
