package jd.persistence.repository;

import jd.persistence.model.Aircraftype;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by eduardom on 10/9/16.
 */
public interface AircraftypeRepository extends JpaRepository<Aircraftype,Long> {
    List<Aircraftype> findBynameContaining(String name);
}
