package jd.persistence.repository;

import jd.persistence.model.Cardcheck;
import org.springframework.data.jpa.repository.JpaRepository;

public interface cardcheckRepository extends JpaRepository<Cardcheck, Long> {
    Cardcheck findByFuelCardCode(String fuelCardCode);
}
