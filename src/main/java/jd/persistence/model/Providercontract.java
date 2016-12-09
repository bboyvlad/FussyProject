package jd.persistence.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by eduardom on 12/9/16.
 */
@Entity
@Table(name="Providercontract")
public class Providercontract {

    private long id;
    private long cprovider;
    private Date fromvalid;
    private Date tovalid;
    private double amountbase;
    private String reference;
    private int diff1;
    private int diff2;
    private int diff3;
    private int vmin;
    private int vmax;
    private int aviation; //commercial and general or between
    private Set<Applycustomer> custom = new HashSet<Applycustomer>(0);

    public Providercontract() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROVIDERCONTRACT_ID", unique = true, nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCprovider() {
        return cprovider;
    }

    public void setCprovider(long cprovider) {
        this.cprovider = cprovider;
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

    public double getAmountbase() {
        return amountbase;
    }

    public void setAmountbase(double amountbase) {
        this.amountbase = amountbase;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public int getDiff1() {
        return diff1;
    }

    public void setDiff1(int diff1) {
        this.diff1 = diff1;
    }

    public int getDiff2() {
        return diff2;
    }

    public void setDiff2(int diff2) {
        this.diff2 = diff2;
    }

    public int getDiff3() {
        return diff3;
    }

    public void setDiff3(int diff3) {
        this.diff3 = diff3;
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

    public int getAviation() {
        return aviation;
    }

    public void setAviation(int aviation) {
        this.aviation = aviation;
    }


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "Contract_Apply", joinColumns = {
            @JoinColumn(name = "PROVIDERCONTRACT_ID", nullable = false, updatable = false) },
            inverseJoinColumns = {@JoinColumn(name = "APPLYCUSTOMER_ID",
                    nullable = false, updatable = false) })
    public Set<Applycustomer> getCustom() {
        return custom;
    }

    public void setCustom(Set<Applycustomer> custom) {
        this.custom = custom;
    }
}
