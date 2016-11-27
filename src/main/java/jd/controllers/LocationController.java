package jd.controllers;

import jd.Util.AppMappings;
import jd.persistence.model.Location;
import jd.persistence.repository.LocationRepository;
import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiPathParam;
import org.jsondoc.core.pojo.ApiStage;
import org.jsondoc.core.pojo.ApiVisibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by eduardom on 10/4/16.
 */
@Controller
@RequestMapping(value = AppMappings.LOCATION)
@Api(
        name="Localidades",
        description = "Establece procedimientos para la gestion de las localidades",
        group = "Ubicaciones",
        visibility = ApiVisibility.PUBLIC,
        stage = ApiStage.RC
)
public class LocationController {

    @Autowired
    LocationRepository locationRepository;

    public LocationController(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    //show all locations
    @RequestMapping(method = RequestMethod.GET)
    @ApiMethod(description = "Recupera una lista de todos las localidades disponibles")
    public @ResponseBody List<Location> showLocations(){
        try{
            return locationRepository.findByAvailable(true);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    //retrieve products chids from group parent
    @RequestMapping(value = "/airport/{tag}",method = RequestMethod.GET)
    @ApiMethod(description = "Recupera una lista de localidades a traves del tag aeropuerto")
    public @ResponseBody List<Location> showAirportsByName(@ApiPathParam(name = "tag", description = "tag nombre del aeropuerto") @PathVariable String tag){
        try{
            //locationRepository.findByNameContainingIgnoreCase(tag);
            if(tag !=null){
                return locationRepository.findByTag(tag);
            }
            return null;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    //retrieve products chids from group parent
    @RequestMapping(value = "{airport_id}",method = RequestMethod.GET)
    @ApiMethod(description = "Recupera una lista de localidades a traves del ID aeropuerto")
    public @ResponseBody Location getAirportsById(@ApiPathParam(name = "airport_id", description = "ID del aeropuerto") @PathVariable long airport_id){
        try{
            return locationRepository.findOne(airport_id);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

}
