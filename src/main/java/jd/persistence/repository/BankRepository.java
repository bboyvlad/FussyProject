package jd.persistence.repository;

import jd.persistence.model.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by eduardom on 11/3/16.
 */
public interface BankRepository extends JpaRepository<Bank, Long> {

    List<Bank> findByDeletedNot(boolean eliminado);

}
