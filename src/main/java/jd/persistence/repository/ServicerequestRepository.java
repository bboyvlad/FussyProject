package jd.persistence.repository;

import jd.persistence.model.Servicerequest;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by eduardom on 10/8/16.
 */
public interface ServicerequestRepository extends JpaRepository<Servicerequest,Long> {
}
