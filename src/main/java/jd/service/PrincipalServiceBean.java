package jd.service;

import jd.persistence.model.Principal;
import jd.persistence.repository.PrincipalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by eduardom on 10/15/16.
 */
@Service
public class PrincipalServiceBean implements PrincipalserviceInt {

    @Autowired
    private PrincipalRepository principalRepository;

    @Override
    public Principal findByEmail(String email){
        Principal principal=principalRepository.findByEmail(email);
        return principal;
    }

}
