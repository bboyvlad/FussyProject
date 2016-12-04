package jd.persistence.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by eduardom on 11/10/16.
 */
@Entity
@Table(name = "Deferedpay")
public class Deferedpay {

    private long id;  //
    private long paymethod; //
    private long servicerequest;
    private String description;
    private String defertype;
    private Date dcreate;
    private Date dupdate;
    private double amount;
    private boolean pending;

    public Deferedpay() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DEFERED_ID", unique = true, nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "PAYMETHOD_ID")
    public long getPaymethod() {
        return paymethod;
    }

    public void setPaymethod(long paymethod) {
        this.paymethod = paymethod;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDefertype() {
        return defertype;
    }

    public void setDefertype(String defertype) {
        this.defertype = defertype;
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Column(name = "SERVICEREQUEST_ID")
    public long getServicerequest() {
        return servicerequest;
    }

    public void setServicerequest(long servicerequest) {
        this.servicerequest = servicerequest;
    }

    public boolean isPending() {
        return pending;
    }

    public void setPending(boolean pending) {
        this.pending = pending;
    }
}

