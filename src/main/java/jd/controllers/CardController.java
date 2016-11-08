package jd.controllers;

import jd.Util.AppMappings;
import jd.persistence.dto.cardValidateDto;
import jd.persistence.model.Cardcheck;

import jd.persistence.repository.cardcheckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by eduardom on 10/24/16.
 */
@RestController
@RequestMapping(value = AppMappings.CARD)
public class CardController {

    @Autowired
    private cardcheckRepository repository;

    // Validation
    @RequestMapping(value = "/validate/{fuelCardCode}", method = RequestMethod.GET)
    public cardValidateDto cardView(@PathVariable("fuelCardCode") final String cardNumber) {

        try{

            final Cardcheck card = repository.findByFuelCardCode(cardNumber);

            if(card!= null){
                cardValidateDto dto= new cardValidateDto();
                dto.setCardCode(card.getCardCode());
                dto.setCardName(card.getCardName());
                dto.setFuelCardCode(card.getFuelCardCode());
                dto.setAircraftCode(card.getAircraftCode());
                dto.setEstatus(card.getEstatus());

                return dto;
            }else{
                System.out.println("No se encontro la tarjeta");
                return null;
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }

    }

    // Balance
    @RequestMapping(value = "/status/{fuelCardCode}", method = RequestMethod.GET)
    public Cardcheck cardBalance(@PathVariable("fuelCardCode") final String cardNumber) {

        try{

            Cardcheck cardBalance=repository.findByFuelCardCode(cardNumber);

            if(cardBalance!=null){
                return repository.findByFuelCardCode(cardNumber);
            }else{
                System.out.println("No se encontro la tarjeta");
                return null;
            }


        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }

    }



}
