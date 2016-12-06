package jd.persistence.repository;

import jd.persistence.model.Aircraftype;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by eduardom on 10/9/16.
 */
public interface AircraftypeRepository extends JpaRepository<Aircraftype,Long> {

    List<Aircraftype> findBynameContaining(String name);

    @Query(value = "SELECT a.AIRCRAFTYPE_ID, a.name, a.manufacture, a.craftype, a.series, a.engines, a.units, a.mrmp, a.mtow, a.owe, a.mzfw, a.mlgw, a.mtank , a.icao , a.iata , a.craftlength , a.craftwidth , a.appspeed , a.wingspan , a.tailheight , a.arc , a.active FROM Aircraftype as a WHERE a.active=1 and (a.name like %?1% or a.manufacture like %?1% or a.craftype like %?1%)",nativeQuery = true)
    List<Aircraftype> findByNameManofactureCraftype(String tag);

}
