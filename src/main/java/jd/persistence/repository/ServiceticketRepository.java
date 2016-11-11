package jd.persistence.repository;

import jd.persistence.model.Serviceticket;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by eduardom on 11/11/16.
 */
public interface ServiceticketRepository extends JpaRepository<Serviceticket, Long> {
}
