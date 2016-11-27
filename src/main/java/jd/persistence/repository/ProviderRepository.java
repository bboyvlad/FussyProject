package jd.persistence.repository;

import jd.persistence.model.Provider;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by eduardom on 11/24/16.
 */
public interface ProviderRepository extends JpaRepository<Provider,Long> {
}
