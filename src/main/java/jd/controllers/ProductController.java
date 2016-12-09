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

    //Create a product with prices
    @RequestMapping(method = RequestMethod.POST)
    public String[] addProduct(@RequestBody newProductDto nproduct){
        return null;
    }



}
