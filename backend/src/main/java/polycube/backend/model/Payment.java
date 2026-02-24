package polycube.backend.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private Order order;

    private int finalAmount;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private Grade paidGrade;

    private LocalDateTime paidAt;

    protected Payment(Order order, int finalAmount, PaymentMethod paymentMethod, Grade paidGrade) {
        this.order = order;
        this.finalAmount = finalAmount;
        this.paymentMethod = paymentMethod;
        this.paidGrade = paidGrade;
        this.paidAt = LocalDateTime.now();
    }

}
