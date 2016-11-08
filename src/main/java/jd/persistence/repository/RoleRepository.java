package jd.persistence.repository;

import jd.persistence.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by eduardom on 10/24/16.
 */
public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByCode(String code);
}
