package polycube.backend.policy;

public interface DiscountPolicy {
    boolean isSupport(DiscountContext context);

    int calculateDiscount(int price);

    default int getPriority() {
        return 1;
    }
}
