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
@Table(name = "Product")
public class Product {
    private long id;
    private long catalog;
    private String name;
    private String detaildesc;
    private boolean poundenable; //mtow
    private boolean maxenable; //max min values
    private Date dcreate;
    private Date dupdate;
    private boolean active;
    private boolean deleted;
    private Set<Locationcontract> locations = new HashSet<Locationcontract>(0);

    public Product() {  }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_ID", unique = true, nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCatalog() {
        return catalog;
    }

    public void setCatalog(long catalog) {
        this.catalog = catalog;
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

    public boolean isPoundenable() {
        return poundenable;
    }

    public void setPoundenable(boolean poundenable) {
        this.poundenable = poundenable;
    }

    public boolean isMaxenable() {
        return maxenable;
    }

    public void setMaxenable(boolean maxenable) {
        this.maxenable = maxenable;
    }

    public Date getDcreate() {
        return dcreate;
    }

    public void setDcreate(Date dcreate) {
        this.dcreate = dcreate;
    }

    public Date getDupdate() {
        return dupdate;
    }

    public void setDupdate(Date dupdate) {
        this.dupdate = dupdate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "Provider_Location", joinColumns = {
            @JoinColumn(name = "LOCATIONCONTRACT_ID", nullable = false, updatable = false) },
            inverseJoinColumns = {@JoinColumn(name = "PROVIDERCONTRACT_ID",
                    nullable = false, updatable = false) })
    public Set<Locationcontract> getLocations() {
        return locations;
    }

    public void setLocations(Set<Locationcontract> locations) {
        this.locations = locations;
    }
}
