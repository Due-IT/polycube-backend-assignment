package polycube.backend.discount.history;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountHistoryRepository extends JpaRepository<DiscountHistory, Long> {
}
