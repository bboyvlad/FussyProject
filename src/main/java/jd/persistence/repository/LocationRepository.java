package jd.persistence.repository;

import jd.persistence.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by eduardom on 10/4/16.
 */

public interface LocationRepository extends JpaRepository<Location, Long> {


    @Query(value = "SELECT l.LOCATION_ID, l.name, l.city, l.country, l.IATA, l.ICAO, l.latitude, l.longitude, l.elevation, l.timezone, l.dst, l.region, l.available FROM Location as l  WHERE l.available=1 and (l.name like %?1% or l.IATA like %?1% or l.ICAO like %?1%)", nativeQuery = true)
    List<Location> findByTag(String tag);

    @Query(value = "SELECT l.LOCATION_ID, l.name, l.city, l.country, l.IATA, l.ICAO, l.latitude, l.longitude, l.elevation, l.timezone, l.dst, l.region, l.available FROM Location as l  WHERE l.name like %?1% or l.IATA like %?1% or l.ICAO like %?1%", nativeQuery = true)
    List<Location> findAllByTag(String tag);

    List<Location> findByNameContainingIgnoreCase(String tag);
    List<Location> findByAvailable(boolean available);
}
