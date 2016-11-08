package jd.controllers;

import jd.Util.AppMappings;
import jd.Util.AppUtils;
import jd.persistence.model.Aviation;
import jd.persistence.model.Price;
import jd.persistence.model.Product;
import jd.persistence.repository.AviationRepository;
import jd.persistence.repository.SvcgroupRepository;
import jd.persistence.repository.PriceRepository;
import jd.persistence.repository.ProductRepository;
import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiPathParam;
import org.jsondoc.core.pojo.ApiStage;
import org.jsondoc.core.pojo.ApiVisibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Hashtable;
import java.util.List;
import java.util.Set;

/**
 * Created by eduardom on 9/13/16.
 */
@RestController
@RequestMapping(value = AppMappings.PRODUCTS)
@Api(
        name="Products",
        description = "Establece procedimientos para la gestion de productos y precios",
        group = "Productos",
        visibility = ApiVisibility.PUBLIC,
        stage = ApiStage.RC
)
public class ProductController {

    ProductRepository productRepository;

    AppUtils utils;

    @Autowired
    SvcgroupRepository svcgroupRepository;

    @Autowired
    AviationRepository aviation;

    @Autowired
    PriceRepository priceRepository;

    @Autowired
    public ProductController(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    //show all products
    @RequestMapping(method = RequestMethod.GET)
    public List<Product> showAllProducts(){
        try{
            return productRepository.findAll();
        }catch(Exception e){
            return null;
        }
    }

    //show all products by group id
    @RequestMapping(value = "/group/{group_id}", method = RequestMethod.GET)
    public Set<Product> showProductsGroup(@PathVariable Long group_id){
        try{
            return svcgroupRepository.findOne(group_id).getProducts();
        }catch(Exception e){
            return null;
        }
    }

    //show products by id
    @RequestMapping(value = "/{product_id}", method = RequestMethod.GET)
    public Object showProduct(@PathVariable Long product_id){
        Hashtable<String, String> message = new Hashtable<String, String>();
        try{
            return productRepository.findOne(product_id);
        }catch(Exception e){
            message.put("message","Producto no encontrado");
            return message;
        }
    }

    //add a price from product
    @RequestMapping(value = "/prices/{product_id}", method = RequestMethod.POST)
    public Object setPrices(@RequestBody Price price, @PathVariable Long product_id){
        Hashtable<String, String> message = new Hashtable<String, String>();
        try {
            Product product= productRepository.findOne(product_id);
            return productRepository.save(product);
        }catch (Exception e){
            message.put("message","Producto no pudo ser actualizado err: "+e.getCause());
            return null;
        }
    }

    //update a product
    @RequestMapping(method = RequestMethod.PUT)
    public Object updateProduct(@RequestBody Product product){
        Hashtable<String, String> message = new Hashtable<String, String>();
        try {
            return productRepository.save(product);
        }catch (Exception e){
            message.put("message","Producto no pudo ser actualizado err: "+e.getCause());
            return null;
        }
    }

    //retrieve a price from product
    @RequestMapping(value = "/prices/{location}/{aviationtype}/{myaircraft}/{landingdate}", method = RequestMethod.GET)
    @ApiMethod(description = "Permite al usuario conocer los servicios disponibles para su aeronave en una localidad especifica")

    public Object getPricesFromLocation(
            @ApiPathParam(name = "location", description= "localidad o destino")  @PathVariable long location,
            @ApiPathParam(name = "aviationtype", description= "Tipo de aviación ej. comercial") long aviationtype,
            @ApiPathParam(name = "landingdate", description= "fecha de aterrizaje") long landingdate,
            @ApiPathParam(name = "myaircraft", description= "aeronave que pertenece al usuario, la que va a viajar") long myaircraft
    ){
        Hashtable<String, String> message = new Hashtable<String, String>();
        try {




            return null ;// productRepository.save(product);
        }catch (Exception e){
            message.put("message","Producto no pudo ser actualizado err: "+e.getCause());
            return null;
        }
    }

    //update a price from product
    @RequestMapping(value = "/prices", method = RequestMethod.PUT)
    public Object updatePrice(@RequestBody Price price){
        Hashtable<String, String> message = new Hashtable<String, String>();
        try {
            return priceRepository.save(price);
        }catch (Exception e){
            message.put("message","precio no pudo ser actualizado err: "+e.getCause());
            return null;
        }
    }

    @RequestMapping(value = "/findbytag/{tag}", method = RequestMethod.GET)
    public Object findByTag(@PathVariable String tag){
        try {
            return productRepository.findBynameContaining(tag);
        }catch (Exception e){
            return null;
        }

    }

    //AVIATION TYPE
    @RequestMapping(value = "/aviationtype", method = RequestMethod.GET)
    public List<Aviation> ShowAviationType(){
        try {
            return aviation.findAll();
        }catch (Exception e){
            return null;
        }

    }

}
