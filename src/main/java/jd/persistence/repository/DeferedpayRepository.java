package jd.persistence.repository;

import jd.persistence.model.Deferedpay;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by eduardom on 11/10/16.
 */
public interface DeferedpayRepository extends JpaRepository<Deferedpay,Long> {
    Deferedpay findByServicerequestIs(long id);
}
