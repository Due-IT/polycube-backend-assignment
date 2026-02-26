package polycube.backend.fixture;

import polycube.backend.discount.setting.DiscountSetting;
import polycube.backend.model.type.Grade;
import polycube.backend.model.type.PaymentMethod;

public class DiscountSettingFixture {
    public static DiscountSetting createNORMALDiscountSetting() {
        return createDiscountSetting(Grade.NORMAL.name(), 0, 0, 1);
    }

    public static DiscountSetting createVIPDiscountSetting() {
        return createDiscountSetting(Grade.VIP.name(), 0, 1000, 1);
    }

    public static DiscountSetting createVVIPDiscountSetting() {
        return createDiscountSetting(Grade.VVIP.name(), 10, 0, 1);
    }

    public static DiscountSetting createPOINTDiscountSetting() {
        return createDiscountSetting(PaymentMethod.POINT.name(), 5, 0, 2);
    }

    private static DiscountSetting createDiscountSetting(String policyName, int discountPercent, int discountAmount, int priority) {
        return new DiscountSetting(
                policyName, discountPercent, discountAmount, priority

        );
    }
}
