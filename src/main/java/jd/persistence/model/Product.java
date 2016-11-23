package jd.persistence.model;

import javax.persistence.*;
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
    private Date dcreate;
    private boolean active;


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
}
