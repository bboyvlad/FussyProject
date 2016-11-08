package jd.service;


import jd.persistence.repository.PrincipalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PrincipalService {

    @Autowired
    private PrincipalRepository principalRepository;

}
