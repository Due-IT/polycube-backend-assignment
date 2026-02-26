package polycube.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import polycube.backend.discount.DiscountPolicyProvider;
import polycube.backend.discount.policy.AppliedDiscountPolicy;
import polycube.backend.model.entity.Member;
import polycube.backend.model.entity.Order;
import polycube.backend.payload.OrderRequest;
import polycube.backend.repository.MemberRepository;
import polycube.backend.repository.OrderRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final DiscountPolicyProvider discountPolicyProvider;

    public Order createOrder(OrderRequest request) {
        Member member = memberRepository.findById(request.memberId())
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));

        Order order = new Order(member, request);

        List<AppliedDiscountPolicy> policies = discountPolicyProvider.getPolicies(order);
        order.applyDiscounts(policies);

        order.pay();

        return orderRepository.save(order);
    }
}
