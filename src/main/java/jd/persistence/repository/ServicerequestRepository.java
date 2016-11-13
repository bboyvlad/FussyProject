package jd.persistence.repository;

import jd.persistence.dto.showServicesRequestDto;
import jd.persistence.model.Servicerequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import javax.persistence.NamedNativeQuery;
import java.util.List;

/**
 * Created by eduardom on 10/8/16.
 */
public interface ServicerequestRepository extends JpaRepository<Servicerequest,Long> {


    List<Servicerequest> findByClosedFalse();
    List<Servicerequest> findByClosedTrue();
    List<Servicerequest> findByPrincipal(long id);

    @Query(value = "select sr.SERVICEREQUEST_ID, sr.Principalname, sr.Locationname, sr.dcreate, sr.dlanding, sr.serialcode " +
            "from ServicesRequestPending AS sr",nativeQuery = true)
    List<Object[]> findPending();

}
