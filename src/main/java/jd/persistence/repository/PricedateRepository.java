package jd.persistence.repository;

import jd.persistence.model.Pricedate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by eduardom on 10/13/16.
 */
public interface PricedateRepository extends JpaRepository<Pricedate,Long> {

    List<Pricedate> findByLocationAndAviationAndFromdateLessThanEqualAndTodateGreaterThanEqual(long location, long aviation, Date fromdate, Date todate);
    ArrayList<Pricedate> findByProduct(long product);
}
