package jd.persistence.repository;

import jd.persistence.model.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by eduardom on 9/13/16.
 */
public interface PriceRepository extends JpaRepository<Price,Long>{

    List<Price> findByLocationAndAviationAndValidtoGreaterThanEqual(long location,long aviation, Date validto);
    ArrayList<Price> findByProduct(long product);

}

