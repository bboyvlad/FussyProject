package jd.service;

import jd.persistence.model.Principal;

/**
 * Created by eduardom on 10/15/16.
 */

public interface Principalservice {
    Principal findByEmail(String email);
}
