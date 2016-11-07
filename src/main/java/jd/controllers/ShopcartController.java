package jd.controllers;

import jd.persistence.dto.ShopcartDTO;
import jd.persistence.model.Itemcart;
import jd.persistence.model.Myaircraft;
import jd.persistence.model.Principal;
import jd.persistence.model.Shopcart;
import jd.persistence.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by eduardom on 9/13/16.
 */
@RestController
@RequestMapping(value = "/cart")
public class ShopcartController {

    ShopcartRepository shopcartRepository;

    @Autowired
    PriceRepository price;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    PrincipalRepository principalRepository;

    @Autowired
    PrincipalRepository pp;

    @Autowired
    public ShopcartController(ShopcartRepository shopcartRepository){
        this.shopcartRepository = shopcartRepository;
    }

    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public Object generateCart(Authentication auth){





        /*
        p.getCart().forEach((cart)->{
            if (cart.getId()==13L){

            }
        });
        */


        //principalRepository.save(p);
        //shopcartRepository.delete(13L);
        //return pp.findByname();
        Principal p=principalRepository.findByEmail(auth.getName());
        boolean found = false;
        for(Iterator<Shopcart> shop = p.getCart().iterator(); shop.hasNext();){
            Shopcart s = shop.next();
            long splitted = s.getId();
            if(splitted==19L){
                shop.remove();
                found = true;
            }
        }

        return principalRepository.save(p).getCart();

    }


}
