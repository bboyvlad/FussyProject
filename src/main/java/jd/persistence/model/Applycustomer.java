package jd.persistence.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by eduardom on 12/9/16.
 */
@Entity
@Table(name="Applycustomer")
public class Applycustomer {
    private long id;
    private long customer;
    private int vmin;
    private int vmax;
    private int diff; //diferencial
    private Date fromvalid;
    private Date tovalid;
    private boolean active;

    public Applycustomer() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "APPLYCUSTOMER_ID", unique = true, nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCustomer() {
        return customer;
    }

    public void setCustomer(long customer) {
        this.customer = customer;
    }

    public int getVmin() {
        return vmin;
    }

    public void setVmin(int vmin) {
        this.vmin = vmin;
    }

    public int getVmax() {
        return vmax;
    }

    public void setVmax(int vmax) {
        this.vmax = vmax;
    }

    public int getDiff() {
        return diff;
    }

    public void setDiff(int diff) {
        this.diff = diff;
    }

    public Date getFromvalid() {
        return fromvalid;
    }

    public void setFromvalid(Date fromvalid) {
        this.fromvalid = fromvalid;
    }

    public Date getTovalid() {
        return tovalid;
    }

    public void setTovalid(Date tovalid) {
        this.tovalid = tovalid;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
