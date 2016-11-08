package jd.service;

import jd.persistence.model.Bank;
import jd.persistence.repository.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by eduardom on 11/3/16.
 */
@Service
@Transactional
public class BankService {

    @Autowired
    BankRepository bank;


}

