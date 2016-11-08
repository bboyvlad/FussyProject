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

/**
 * Created by eduardom on 10/9/16.
 */
@RestController
@RequestMapping(value = AppMappings.MYAIRCRAFT)
@Api(
        name="Myaircraft",
        description = "Gestiona las aeronaves que posee un Principal(usuario) en el sistema",
        group = "Aeronaves",
        visibility = ApiVisibility.PUBLIC,
        stage = ApiStage.RC
)
public class MyaircraftController {

    @Autowired
    MyaircraftRepository myaircraftRepository;

    @Autowired
    AircraftypeRepository aircraftypeRepository;

    @Autowired
    public MyaircraftController(MyaircraftRepository myaircraftRepository) {
        this.myaircraftRepository = myaircraftRepository;
    }

}
