package jd.persistence.repository;

import jd.persistence.model.Paymethod;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by eduardom on 9/6/16.
 */
public interface PaymethodRepository extends JpaRepository<Paymethod,Long>{
    Paymethod findBypayacctnum(String payacctnum);
}
