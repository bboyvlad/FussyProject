package jd.controllers;

import jd.Util.AppMappings;
import jd.Util.AppUtils;
import jd.persistence.dto.newProductDto;
import jd.persistence.model.*;
import jd.persistence.repository.*;
import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiPathParam;
import org.jsondoc.core.pojo.ApiStage;
import org.jsondoc.core.pojo.ApiVisibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
    ProviderRepository providers;

    @Autowired
    AviationRepository aviation;

    @Autowired
    PriceRepository priceRepository;

    @Autowired
    PricepoundRepository pricepound;

    @Autowired
    PricedateRepository pricedate;

    @Autowired
    public ProductController(ProductRepository productRepository){
        this.productRepository = productRepository;
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

    /*CRUD de productos*/

    //Create a product with prices
    @RequestMapping(method = RequestMethod.POST)
    public String[] addProduct(@RequestBody newProductDto nproduct){


        try{
            ArrayList<Price> pricesUnit= new ArrayList<>();
            ArrayList<Pricedate> pricesDate= new ArrayList<>();
            ArrayList<Pricepound> pricesPound= new ArrayList<>();

            Product pro = new Product();
            pro.setName(nproduct.getProductname());
            pro.setDetaildesc(nproduct.getDetail());
            pro.setDcreate(new Date());
            pro.setUnit(nproduct.getProductunit());
            pro.setUnitdesc(nproduct.getProductunitdesc());

            pro.setActive(nproduct.isActive());
            pro.setPricetype(nproduct.getPricetype());

            productRepository.saveAndFlush(pro);

            /*Este codigo esta optimizado en el update., pero aqui ya esta asi XD*/
            switch (nproduct.getPricetype()){

                case "U":

                    nproduct.getPrices().forEach((price)->{
                    /*Un Objeto de tipo precio Unitario*/
                        Price pUnit= new Price();

                        pUnit.setProduct(pro.getId());
                        pUnit.setAviation(price.getAviation());
                        pUnit.setProvider(price.getProvider());
                        pUnit.setLocation(price.getLocation());
                        pUnit.setName(nproduct.getProductname());
                        pUnit.setPrepaid(price.isPrepaid());

                        pUnit.setValidto(price.getValidto());

                        pUnit.setCurrency(price.getCurrency());
                        pUnit.setMeasure(price.getMeasure());
                        pUnit.setUnit(price.getUnit());
                        pUnit.setUnitdesc(nproduct.getProductunitdesc());
                        pUnit.setPrice1(price.getPrice1());
                        pUnit.setPrice2(price.getPrice2());
                        pUnit.setPrice3(price.getPrice3());
                        pUnit.setCost1(price.getCost1());
                        pUnit.setCost2(price.getCost2());
                        pUnit.setCost3(price.getCost3());
                        pUnit.setDiff((price.getDiff()));
                        pUnit.setPerc(price.getPerc());
                        pUnit.setMargin(price.getMargin());
                        pUnit.setMinimun(price.getMinimun());
                        pUnit.setMaximun(price.getMaximun());
                        pUnit.setFeesenable(price.isFeesenable());
                        pUnit.setLocfee(price.getLocfee());
                        pUnit.setIntopfee(price.getIntopfee());
                        pUnit.setOthfee(price.getOthfee());
                        pUnit.setFeename(price.getFeename());

                    /*Lo añado a la lista de precios*/
                        pricesUnit.add(pUnit);
                    });

                    priceRepository.save(pricesUnit);

                    break;

                case "D":

                    nproduct.getPrices().forEach((price)->{

                    /*Un Objeto de tipo precio pro fecha*/
                        Pricedate pDate= new Pricedate();

                        pDate.setProduct(pro.getId());
                        pDate.setAviation(price.getAviation());
                        pDate.setProvider(price.getProvider());
                        pDate.setLocation(price.getLocation());
                        pDate.setName(nproduct.getProductname());
                        pDate.setPrepaid(price.isPrepaid());

                        pDate.setFromdate(price.getFromdate());
                        pDate.setTodate(price.getTodate());

                        pDate.setCurrency(price.getCurrency());
                        pDate.setMeasure(price.getMeasure());
                        pDate.setUnit(price.getUnit());
                        pDate.setUnitdesc(nproduct.getProductunitdesc());
                        pDate.setPrice1(price.getPrice1());
                        pDate.setPrice2(price.getPrice2());
                        pDate.setPrice3(price.getPrice3());
                        pDate.setCost1(price.getCost1());
                        pDate.setCost2(price.getCost2());
                        pDate.setCost3(price.getCost3());
                        pDate.setDiff((price.getDiff()));
                        pDate.setPerc(price.getPerc());
                        pDate.setMargin(price.getMargin());
                        pDate.setMinimun(price.getMinimun());
                        pDate.setMaximun(price.getMaximun());
                        pDate.setFeesenable(price.isFeesenable());
                        pDate.setLocfee(price.getLocfee());
                        pDate.setIntopfee(price.getIntopfee());
                        pDate.setOthfee(price.getOthfee());
                        pDate.setFeename(price.getFeename());

                    /*Lo añado a la lista de precios pro fecha*/
                        pricesDate.add(pDate);
                    });

                    pricedate.save(pricesDate);

                    break;

                case "P":
                    nproduct.getPrices().forEach((price)->{

                    /*Un Objeto de tipo precio pro pound*/
                        Pricepound pPound= new Pricepound();

                        pPound.setProduct(pro.getId());
                        pPound.setAviation(price.getAviation());
                        pPound.setProvider(price.getProvider());
                        pPound.setLocation(price.getLocation());
                        pPound.setName(nproduct.getProductname());
                        pPound.setPrepaid(price.isPrepaid());

                        pPound.setFrompound(price.getFrompound());
                        pPound.setTopound(price.getTopound());

                        pPound.setCurrency(price.getCurrency());
                        pPound.setMeasure(price.getMeasure());
                        pPound.setUnit(price.getUnit());
                        pPound.setUnitdesc(nproduct.getProductunitdesc());
                        pPound.setPrice1(price.getPrice1());
                        pPound.setPrice2(price.getPrice2());
                        pPound.setPrice3(price.getPrice3());
                        pPound.setCost1(price.getCost1());
                        pPound.setCost2(price.getCost2());
                        pPound.setCost3(price.getCost3());
                        pPound.setDiff((price.getDiff()));
                        pPound.setPerc(price.getPerc());
                        pPound.setMargin(price.getMargin());
                        pPound.setMinimun(price.getMinimun());
                        pPound.setMaximun(price.getMaximun());
                        pPound.setFeesenable(price.isFeesenable());
                        pPound.setLocfee(price.getLocfee());
                        pPound.setIntopfee(price.getIntopfee());
                        pPound.setOthfee(price.getOthfee());
                        pPound.setFeename(price.getFeename());

                    /*Lo añado a la lista de precios pro pound*/
                        pricesPound.add(pPound);
                    });

                    pricepound.save(pricesPound);

                    break;

            }

            return new String[]{"message","success"};
        }catch (Exception e){
            System.out.println(e);
            return new String[]{"message","failure"};
        }

    }

    //retrieve all products
    @RequestMapping(method = RequestMethod.GET)
    public List<Product> showProducts(){
        try{
            return productRepository.findAll();
        }catch(Exception e){
            return null;
        }
    }

    //Retieve a product
    @RequestMapping(value = "/{product}",method = RequestMethod.GET)
    public Product showProduct(@PathVariable long product){

        ArrayList<Price> pricesUnit= new ArrayList<>();
        ArrayList<Pricedate> pricesDate= new ArrayList<>();
        ArrayList<Pricepound> pricesPound= new ArrayList<>();

        Product pro=productRepository.findOne(product);

        Product shProduct = new Product();

        shProduct.setId(pro.getId());
        shProduct.setName(pro.getName());
        shProduct.setDetaildesc(pro.getDetaildesc());
        shProduct.setDcreate(pro.getDcreate());
        shProduct.setPricetype(pro.getPricetype());

        switch (pro.getPricetype()){

            case "U":
                pricesUnit=priceRepository.findByProduct(pro.getId());
                shProduct.setPricesUnit(pricesUnit);
                break;

            case "P":
                pricesPound=pricepound.findByProduct(pro.getId());
                shProduct.setPricesPound(pricesPound);
                break;

            case "D":
                pricesDate=pricedate.findByProduct(pro.getId());
                shProduct.setPricesDate(pricesDate);
                break;
        }

        return shProduct;

    }

    @RequestMapping(method = RequestMethod.PATCH)
    public String[] updateProduct(@RequestBody Product pro){

        ArrayList<Price> pricesUnit= new ArrayList<>();
        ArrayList<Pricedate> pricesDate= new ArrayList<>();
        ArrayList<Pricepound> pricesPound= new ArrayList<>();

        Product p= new Product();

        p.setId(pro.getId());
        p.setName(pro.getName());
        p.setDetaildesc(pro.getDetaildesc());
        p.setPricetype(pro.getPricetype());
        p.setDcreate(pro.getDcreate());
        p.setDupdate(new Date());
        p.setActive(pro.isActive());

        try{
            switch (p.getPricetype()){

                case "U":
                    pricesUnit=pro.getPricesUnit();
                    priceRepository.save(pricesUnit);
                    break;
                case "P":
                    pricesPound=pro.getPricesPound();
                    pricepound.save(pricesPound);
                    break;
                case "D":
                    pricesDate=pro.getPricesDate();
                    pricedate.save(pricesDate);
                    break;
            }
            productRepository.saveAndFlush(p);
            return new String[]{"message","success"};
        }catch (Exception e){
            return new String[]{"message","failure"};
        }
    }

    @RequestMapping(value = "/{product}",method = RequestMethod.DELETE)
    public String[] softDeleteProduct(@PathVariable long product){

        Product pro=productRepository.findOne(product);
        pro.setDupdate(new Date());
        pro.setDeleted(true);
        productRepository.save(pro);

        return new String[]{"message","success"};

    }

    /*End CRUD*/

    /*Show Providers*/
    @RequestMapping(value = "/providers",method = RequestMethod.GET)
    public List<Provider> showProviders(){
        return providers.findAll();
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
