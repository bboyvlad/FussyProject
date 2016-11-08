package jd.persistence.model;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by eduardom on 9/5/16.
 */
@Entity
@Table(name = "Paymethod",uniqueConstraints = {
        @UniqueConstraint(columnNames = "PAYMETHOD_ID")})
public class Paymethod {

    private Long payid;
    private String paytype;
    private String paystatus;
    private boolean payenabled;
    private String payacctnum;
    private String paycardname;
    private String paycardtype;
    private double paybalance;
    private double payblocked;
    private double payavailable;
    private boolean deleted;
    private Date paycreate;
    private Date payvalid;
    private String paycardrandomcode;
    private String paycardseccode;
    private String notes;
    private Set<Tranpay> transactionspayments = new HashSet<Tranpay>(0);

    public Paymethod() {}

    public Paymethod(Long payid, String paytype, String paystatus, boolean payenabled, String payacctnum, String paycardname, double paybalance, double payblocked, double payavailable, Date paycreate, Date payvalid, String paycardrandomcode, String paycardseccode, String notes) {
        this.payid = payid;
        this.paytype = paytype;
        this.paystatus = paystatus;
        this.payenabled = false;
        this.payacctnum = payacctnum;
        this.paycardname = paycardname;
        this.paybalance = paybalance;
        this.payblocked = payblocked;
        this.payavailable = payavailable;
        this.paycreate = paycreate;
        this.payvalid = payvalid;
        this.paycardrandomcode = paycardrandomcode;
        this.paycardseccode = paycardseccode;
        this.notes = notes;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PAYMETHOD_ID", unique = true, nullable = false)
    public Long getPayid() {
        return payid;
    }

    public void setPayid(Long payid) {
        this.payid = payid;
    }

    public String getPaytype() {
        return paytype;
    }

    public void setPaytype(String paytype) {
        this.paytype = paytype;
    }

    public String getPaystatus() {
        return paystatus;
    }

    public void setPaystatus(String paystatus) {
        this.paystatus = paystatus;
    }

    public boolean isPayenabled() {
        return payenabled;
    }

    public void setPayenabled(boolean payenabled) {
        this.payenabled = payenabled;
    }

    public String getPayacctnum() {
        return payacctnum;
    }

    public void setPayacctnum(String payacctnum) {
        this.payacctnum = payacctnum;
    }

    public String getPaycardname() {
        return paycardname;
    }

    public void setPaycardname(String paycardname) {
        this.paycardname = paycardname;
    }

    public double getPaybalance() {
        return paybalance;
    }

    public void setPaybalance(double paybalance) {
        this.paybalance = paybalance;
    }

    public Date getPaycreate() {
        return paycreate;
    }

    public void setPaycreate(Date paycreate) {
        this.paycreate = paycreate;
    }

    public Date getPayvalid() {
        return payvalid;
    }

    public void setPayvalid(Date payvalid) {
        this.payvalid = payvalid;
    }

    public String getPaycardrandomcode() {
        return paycardrandomcode;
    }

    public void setPaycardrandomcode(String paycardrandomcode) {
        this.paycardrandomcode = paycardrandomcode;
    }

    public String getPaycardseccode() {
        return paycardseccode;
    }

    public void setPaycardseccode(String paycardseccode) {
        this.paycardseccode = paycardseccode;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "Paymethod_Tranpay", joinColumns = {
            @JoinColumn(name = "PAYMETHOD_ID", nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "TRANPAY_ID",
                    nullable = false, updatable = false) })
    public Set<Tranpay> getTransactionspayments() {
        return transactionspayments;
    }

    public void setTransactionspayments(Set<Tranpay> transactionspayments) {
        this.transactionspayments = transactionspayments;
    }

    public double getPayblocked() {
        return payblocked;
    }

    public void setPayblocked(double payblocked) {
        this.payblocked = payblocked;
    }

    public String getPaycardtype() {
        return paycardtype;
    }

    public void setPaycardtype(String paycardtype) {
        this.paycardtype = paycardtype;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Transient
    public double getPayavailable() {
        double available=this.paybalance-this.payblocked;
        return available;
    }

    public void setPayavailable(double payavailable) {
        this.payavailable = payavailable;
    }
}
