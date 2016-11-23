package jd.persistence.repository;

import jd.persistence.model.Pricepound;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eduardom on 10/13/16.
 */
public interface PricepoundRepository extends JpaRepository<Pricepound,Long> {

    List<Pricepound> findByLocationAndAviationAndFrompoundLessThanEqualAndTopoundGreaterThanEqual(long location, long aviation,double frompound, double topound);

    ArrayList<Pricepound> findByProduct(long product);

}
