package jd.persistence.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by eduardom on 11/3/16.
 */


@Entity
@Table(name = "TransactionBank")
public class TransactionBank {

    private long banktransid;
    private long paymethod; //paymethod related
    private String relatedref;
    private String insbank; //instructing bank
    private String insacctnum; //instructing acct num
    private double amount;
    private Date dcreate;
    private Date dupdate;
    private boolean checked;

    public TransactionBank() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TRANSBANK_ID", unique = true, nullable = false)
    public long getBanktransid() {
        return banktransid;
    }

    public void setBanktransid(long banktransid) {
        this.banktransid = banktransid;
    }

    @Column(name = "PAYMETHOD_ID")
    public long getPaymethod() {
        return paymethod;
    }

    public void setPaymethod(long paymethod) {
        this.paymethod = paymethod;
    }

    public String getRelatedref() {
        return relatedref;
    }

    public void setRelatedref(String relatedref) {
        this.relatedref = relatedref;
    }

    public String getInsbank() {
        return insbank;
    }

    public void setInsbank(String insbank) {
        this.insbank = insbank;
    }

    public String getInsacctnum() {
        return insacctnum;
    }

    public void setInsacctnum(String insacctnum) {
        this.insacctnum = insacctnum;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
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

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

}
