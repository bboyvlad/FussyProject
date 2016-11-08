package jd.persistence.repository;

import jd.persistence.model.Myaircraft;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by eduardom on 10/9/16.
 */
public interface MyaircraftRepository extends JpaRepository<Myaircraft,Long> {
}
