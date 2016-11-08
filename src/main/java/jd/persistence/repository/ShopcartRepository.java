package jd.persistence.repository;

import jd.persistence.model.Shopcart;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by eduardom on 9/13/16.
 */
public interface ShopcartRepository extends JpaRepository<Shopcart, Long>{

}
