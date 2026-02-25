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

        Order order = new Order(member, request.itemName(), request.price());

        DiscountPolicy policy = discountPolicyProvider.getPolicy(member.getGrade());
        order.applyDiscount(policy);

        Payment payment = order.pay(request.paymentMethod());

        return paymentRepository.save(payment);
    }
}
