package jd.persistence.repository;

import jd.persistence.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by eduardom on 10/4/16.
 */

public interface LocationRepository extends JpaRepository<Location, Long> {
    List<Location> findByNameContainingIgnoreCase(String name);
    List<Location> findByAvailable(boolean available);
}
