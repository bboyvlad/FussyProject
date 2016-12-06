package jd.persistence.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by eduardom on 11/3/16.
 */
@Entity
@Table(name = "Receivedpay")
public class Receivedpay {

    private long id;
    private long paymethod; //paymethod related
    private long bank;

    private String relatedref;

    private long inspaymethod;
    private String insbank; //instructing bank
    private String insacctnum; //instructing acct num
    private String insname;

    private double amount;
    private double famount;

    private Date dcreate;
    private Date dupdate;
    private boolean approved;

    public Receivedpay() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PAYMENT_ID", unique = true, nullable = false)
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

    @Column(name = "BANK_ID")
    public long getBank() {
        return bank;
    }

    public void setBank(long bank) {
        this.bank = bank;
    }

    public String getRelatedref() {
        return relatedref;
    }

    public void setRelatedref(String relatedref) {
        this.relatedref = relatedref;
    }

    public long getInspaymethod() {
        return inspaymethod;
    }

    public void setInspaymethod(long inspaymethod) {
        this.inspaymethod = inspaymethod;
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

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public String getInsname() {
        return insname;
    }

    public void setInsname(String insname) {
        this.insname = insname;
    }

    public double getFamount() {
        return famount;
    }

    public void setFamount(double famount) {
        this.famount = famount;
    }
}
