package jd.controllers;

import jd.Util.AppMappings;
import jd.persistence.model.Svcgroup;
import jd.persistence.model.Product;
import jd.persistence.repository.SvcgroupRepository;
import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.pojo.ApiStage;
import org.jsondoc.core.pojo.ApiVisibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Hashtable;
import java.util.List;

/**
 * Created by eduardom on 9/12/16.
 */
@RestController
@RequestMapping(value = AppMappings.CATALOG)
@Api(
        name="Svcgroup",
        description = "Definicion y clasificacion de grupos de serviciosen el sistema",
        group = "Grupo de Servicios",
        visibility = ApiVisibility.PUBLIC,
        stage = ApiStage.RC
)
public class CatalogController {

    @Autowired
    SvcgroupRepository svcgroupRepository;

    public CatalogController(SvcgroupRepository svcgroupRepository) {
        this.svcgroupRepository = svcgroupRepository;
    }

    //Create a new group service
    @RequestMapping(method = RequestMethod.POST)
    public Object createGroup(@RequestBody Svcgroup grp){
        try{
            return svcgroupRepository.save(grp);
        }catch(Exception e){
            return e.getMessage();
        }
    }

    //show all groupservices
    @RequestMapping(method = RequestMethod.GET)
    public List<Svcgroup> showProducts(){
        try{
            return svcgroupRepository.findAll();
        }catch(Exception e){
            return null;
        }
    }

    //retrieve a group service by id
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Object showGroup(@PathVariable Long id){
        try{
            return svcgroupRepository.findOne(id);
        }catch(Exception e){
            return e.getMessage();
        }
    }

    //Update a group service by id - embedded into json
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Object updateGroup(@RequestBody Svcgroup grp){
        try{
            return svcgroupRepository.save(grp);
        }catch(Exception e){
            return e.getMessage();
        }
    }

    //remove group and childs
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Object deleteGroup(@PathVariable Long id){
        Hashtable<String, String> message = new Hashtable<String, String>();
        try{
            svcgroupRepository.delete(id);
            return message.put("message","Registro eliminado con exito");
        }catch (Exception e){
            return message.put("message","Lo sentimos, no se pudo eliminar porque "+e.getCause());
        }
    }

    //add a product to specific group
    @RequestMapping(value = "/product/{groupid}", method = RequestMethod.POST)
    public Object addProductGroup(@RequestBody Product product, @PathVariable Long groupid){
        try{
            Svcgroup grp = svcgroupRepository.findOne(groupid);
            grp.getProducts().add(product);
            return svcgroupRepository.save(grp);
        }catch(Exception e){
            return e.getMessage();
        }
    }

    //retrieve products chids from group parent
    @RequestMapping(value = "/product/{groupid}", method = RequestMethod.GET)
    public Object showProductGroup(@PathVariable Long groupid){
        try{
            return svcgroupRepository.findOne(groupid).getProducts();
        }catch(Exception e){
            return e.getMessage();
        }
    }

    //remove products chids from group parent
    @RequestMapping(value = "/product/{groupid}/{productid}", method = RequestMethod.DELETE)
    public Object deleteProductGroup(@PathVariable Long groupid, Long productid){
        try{
            //BETA
            return svcgroupRepository.findOne(groupid).getProducts().remove(productid);
        }catch(Exception e){
            return e.getMessage();
        }
    }


}
