package jd.controllers;

import jd.persistence.model.Tranpay;
import jd.persistence.repository.TranpayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by eduardom on 9/5/16.
 */

@Controller
@RequestMapping(value="/paytransactions")
public class TransactionController {

    TranpayRepository tranpayRepository;

    @Autowired
    public TransactionController(TranpayRepository tranpayRepository){
        this.tranpayRepository = tranpayRepository;
    }

    @RequestMapping(value="/create", method = RequestMethod.POST)
    public @ResponseBody Object createPay(@RequestBody Tranpay trans){
        try {
           return tranpayRepository.save(trans);
        }catch (Exception e){
            return e.getLocalizedMessage();
        }
    }

    @RequestMapping(value="/show/{trans_id}")
    public @ResponseBody
    Tranpay getTrans(@PathVariable Long trans_id){
        try{
            return tranpayRepository.findOne(trans_id);
        }catch (Exception e){
            return null;
        }
    }

    @RequestMapping(value="/showall", method = RequestMethod.GET)
    public @ResponseBody List<Tranpay> getAll(){
        try {
            return tranpayRepository.findAll();
        }catch (Exception e){
            return null;
        }
    }




}
