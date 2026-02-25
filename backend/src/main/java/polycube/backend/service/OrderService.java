package polycube.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import polycube.backend.model.entity.Member;
import polycube.backend.model.entity.Order;
import polycube.backend.model.entity.Payment;
import polycube.backend.payload.OrderRequest;
import polycube.backend.policy.DiscountPolicy;
import polycube.backend.policy.DiscountPolicyProvider;
import polycube.backend.repository.MemberRepository;
import polycube.backend.repository.PaymentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final MemberRepository memberRepository;
    private final PaymentRepository paymentRepository;
    private final DiscountPolicyProvider discountPolicyProvider;

    public Payment createOrder(OrderRequest request) {
        Member member = memberRepository.findById(request.memberId())
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));

        Order order = new Order(member, request);

        List<DiscountPolicy> policies = discountPolicyProvider.getPolicies(order);
        order.applyDiscounts(policies);

        Payment payment = order.pay(request.paymentMethod());

        return paymentRepository.save(payment);
    }
}
