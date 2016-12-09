package jd.persistence.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by eduardom on 12/9/16.
 */
@Entity
@Table(name="Fee")
public class Fee {
    private long id;
    private String feename;
    private String concept;
    private int vmin;
    private int vmax;
    private int quantity;
    private Date fromvalid;
    private Date tovalid;
    private double amountfee;
    private boolean active;

    public Fee() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FEE_ID", unique = true, nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFeename() {
        return feename;
    }

    public void setFeename(String feename) {
        this.feename = feename;
    }

    public String getConcept() {
        return concept;
    }

    public void setConcept(String concept) {
        this.concept = concept;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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

    public double getAmountfee() {
        return amountfee;
    }

    public void setAmountfee(double amountfee) {
        this.amountfee = amountfee;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
