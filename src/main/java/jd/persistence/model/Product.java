package jd.persistence.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by eduardom on 9/12/16.
 */

@Entity
@Table(name = "Productfussy")
public class Product {
    private Long id;
    private String name;
    private String detaildesc;
    private String pricetype;
    private String unit;
    private String unitdesc;
    private Date dcreate;
    private Date dupdate;
    private boolean active;
    private boolean deleted;

    ArrayList<Price> pricesUnit= new ArrayList<>();
    ArrayList<Pricedate> pricesDate= new ArrayList<>();
    ArrayList<Pricepound> pricesPound= new ArrayList<>();


    public Product() {  }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_ID", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetaildesc() {
        return detaildesc;
    }

    public void setDetaildesc(String detaildesc) {
        this.detaildesc = detaildesc;
    }

    public Date getDcreate() {
        return dcreate;
    }

    public void setDcreate(Date dcreate) {
        this.dcreate = dcreate;
    }

    public String getPricetype() {
        return pricetype;
    }

    public void setPricetype(String pricetype) {
        this.pricetype = pricetype;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getDupdate() {
        return dupdate;
    }

    public void setDupdate(Date dupdate) {
        this.dupdate = dupdate;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnitdesc() {
        return unitdesc;
    }

    public void setUnitdesc(String unitdesc) {
        this.unitdesc = unitdesc;
    }

    @Transient
    public ArrayList<Price> getPricesUnit() {
        return pricesUnit;
    }

    public void setPricesUnit(ArrayList<Price> pricesUnit) {
        this.pricesUnit = pricesUnit;
    }
    @Transient
    public ArrayList<Pricedate> getPricesDate() {
        return pricesDate;
    }

    public void setPricesDate(ArrayList<Pricedate> pricesDate) {
        this.pricesDate = pricesDate;
    }
    @Transient
    public ArrayList<Pricepound> getPricesPound() {
        return pricesPound;
    }

    public void setPricesPound(ArrayList<Pricepound> pricesPound) {
        this.pricesPound = pricesPound;
    }
}
