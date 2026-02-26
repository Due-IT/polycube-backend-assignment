package polycube.backend.integrate;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import polycube.backend.discount.history.DiscountHistory;
import polycube.backend.discount.history.DiscountHistoryRepository;
import polycube.backend.discount.policy.AppliedDiscountPolicy;
import polycube.backend.discount.policy.DiscountPolicy;
import polycube.backend.discount.policy.VIPDiscountPolicy;
import polycube.backend.discount.setting.DiscountSetting;
import polycube.backend.discount.setting.DiscountSettingRepository;
import polycube.backend.fixture.MemberFixture;
import polycube.backend.fixture.OrderFixture;
import polycube.backend.model.entity.Member;
import polycube.backend.model.entity.Order;
import polycube.backend.model.type.Grade;
import polycube.backend.repository.OrderRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class DiscountIntegrateTest {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private DiscountHistoryRepository discountHistoryRepository;

    @Autowired
    private DiscountSettingRepository discountSettingRepository;

    @BeforeEach
    void setupData() {
        // DB에 실제 데이터 미리 저장
        discountSettingRepository.save(new DiscountSetting("VIP", 0, 1000, 1));
    }

    @Test
    @DisplayName("할인정책이 수정되어도 과거 결제 이력은 보존되어야 한다")
    void history_update_preservation_test() {
        // given
        DiscountPolicy policy = new VIPDiscountPolicy();
        String policyName = policy.getTarget(); // VIP
        DiscountSetting setting = discountSettingRepository.findByPolicyName(policyName).get();
        int discountAmount = setting.getDiscountAmount(); // 1000원 할인
        AppliedDiscountPolicy appliedDiscountPolicy = new AppliedDiscountPolicy(policy, setting);

        Member member = MemberFixture.createMember(Grade.VIP);
        Order order = OrderFixture.createOrder(member, 10000);

        order.applyDiscounts(List.of(appliedDiscountPolicy));
        Order savedOrder = orderRepository.save(order);
        Long historyId = savedOrder.getDiscountHistories().getFirst().getId();

        // when
        String updatedPolicyName = "VIP 특별 할인";
        int updatedDiscountAmount = 2000;
        setting.update(updatedPolicyName, 0, updatedDiscountAmount, 1);

        discountSettingRepository.save(setting);

        // then
        DiscountHistory recordedHistory = discountHistoryRepository.findById(historyId).orElseThrow();

        assertThat(recordedHistory.getPolicyDiscountAmount()).isEqualTo(discountAmount);
        assertThat(recordedHistory.getAppliedDiscountAmount()).isEqualTo(discountAmount);
        assertThat(recordedHistory.getPolicyName()).isEqualTo(policyName);
    }

    @Test
    @DisplayName("할인정책이 삭제되어도 과거 결제 이력은 보존되어야 한다")
    void history_delete_preservation_test() {
        // given
        DiscountPolicy policy = new VIPDiscountPolicy();
        String policyName = policy.getTarget(); // VIP
        DiscountSetting setting = discountSettingRepository.findByPolicyName(policyName).get();
        int discountAmount = setting.getDiscountAmount(); // 1000원 할인
        AppliedDiscountPolicy appliedDiscountPolicy = new AppliedDiscountPolicy(policy, setting);

        Member member = MemberFixture.createMember(Grade.VIP);
        Order order = OrderFixture.createOrder(member, 10000);

        order.applyDiscounts(List.of(appliedDiscountPolicy));
        Order savedOrder = orderRepository.save(order);
        Long historyId = savedOrder.getDiscountHistories().getFirst().getId();

        // when
        discountSettingRepository.delete(setting);

        // then
        DiscountHistory recordedHistory = discountHistoryRepository.findById(historyId).orElseThrow();

        assertThat(recordedHistory.getPolicyDiscountAmount()).isEqualTo(discountAmount);
        assertThat(recordedHistory.getAppliedDiscountAmount()).isEqualTo(discountAmount);
        assertThat(recordedHistory.getPolicyName()).isEqualTo(policyName);
    }
}
