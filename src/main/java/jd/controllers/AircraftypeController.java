package jd.controllers;

import jd.Util.AppMappings;
import jd.persistence.dto.MyaircraftDTO;
import jd.persistence.model.Aircraftype;
import jd.persistence.model.Myaircraft;
import jd.persistence.repository.AircraftypeRepository;
import jd.persistence.repository.MyaircraftRepository;
import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiPathParam;
import org.jsondoc.core.pojo.ApiStage;
import org.jsondoc.core.pojo.ApiVisibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Hashtable;
import java.util.List;

/**
 * Created by eduardom on 10/9/16.
 */
@RestController
@RequestMapping(value = AppMappings.AIRCRAFTYPE)
@Api(
        name="Aircraftype",
        description = "Gestiona las aeronaves predefinidas en la base de datos del sistema",
        group = "Aeronaves",
        visibility = ApiVisibility.PUBLIC,
        stage = ApiStage.RC
)
public class AircraftypeController {

    AircraftypeRepository aircraftypeRepository;

    @Autowired
    public AircraftypeController(AircraftypeRepository aircraftypeRepository) {
        this.aircraftypeRepository = aircraftypeRepository;
    }

    @RequestMapping(value = "/aircraftbyid/{aircraftype_id}",method = RequestMethod.GET)
    @ApiMethod(description = "Recupera un tipo de aeronave segun su identificador en la base de datos")
    public Aircraftype getaircraftype(@ApiPathParam(name = "aircraftype_id", description= "id del tipo de aeronave") @PathVariable long aircraftype_id ) {
        try{
            return aircraftypeRepository.findOne(aircraftype_id);
        }catch(Exception e){
            System.out.println(e.getLocalizedMessage());
        }
        return null;
    }

    @RequestMapping(value = "/aircraftbytag/{tag}",method = RequestMethod.GET)
    @ApiMethod(description = "Recupera un listado de aeronaves segun el TAG enviado")
    public List<Aircraftype> getaircraftypebyname(@ApiPathParam(name = "tag", description= "nombre del aeropuerto") @PathVariable String tag) {
        try{
            return aircraftypeRepository.findByNameManofactureCraftype(tag);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
