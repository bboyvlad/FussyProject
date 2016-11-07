package jd.persistence.repository;

import jd.persistence.model.Giftcard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by eduardom on 10/3/16.
 */

public interface GiftcardRepository extends JpaRepository<Giftcard, Long> {
    Giftcard findByclaimcode(String claim);
}

