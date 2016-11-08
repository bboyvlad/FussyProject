package jd.persistence.repository;

import jd.persistence.model.Principal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by eduardom on 9/3/16.
 */

@Repository
public interface PrincipalRepository extends JpaRepository <Principal,Long>{

    Principal findByEmail(String email);

}
