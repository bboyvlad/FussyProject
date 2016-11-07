package jd.service;

import jd.persistence.model.Principal;
import org.springframework.stereotype.Service;

/**
 * Created by eduardom on 10/20/16.
 */
@Service
public interface PrincipalserviceInt {
    Principal findByEmail(String email);
}
