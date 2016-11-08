package jd.persistence.repository;

import jd.persistence.model.Servicerequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by eduardom on 10/8/16.
 */
public interface ServicerequestRepository extends JpaRepository<Servicerequest,Long> {
    List<Servicerequest> findByClosedFalse();
    List<Servicerequest> findByClosedTrue();
}
