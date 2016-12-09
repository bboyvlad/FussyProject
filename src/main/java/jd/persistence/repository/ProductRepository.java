package jd.persistence.repository;

import jd.persistence.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by eduardom on 9/3/16.
 */
public interface ProductRepository extends JpaRepository <Product,Long>{

}
