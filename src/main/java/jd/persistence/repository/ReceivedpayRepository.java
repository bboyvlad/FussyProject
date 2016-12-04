package jd.persistence.repository;

import jd.persistence.model.Receivedpay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by eduardom on 11/3/16.
 */
public interface ReceivedpayRepository extends JpaRepository<Receivedpay, Long> {
    List<Receivedpay> findByApprovedFalse();
}
