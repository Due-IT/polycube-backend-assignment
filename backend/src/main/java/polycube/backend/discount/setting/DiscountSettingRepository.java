package polycube.backend.discount.setting;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiscountSettingRepository extends JpaRepository<DiscountSetting, String> {
    public Optional<DiscountSetting> findByPolicyName(String policyName);
}
