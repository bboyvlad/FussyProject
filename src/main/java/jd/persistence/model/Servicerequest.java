package jd.persistence.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Created by eduardom on 10/8/16.
 */
@Entity
@Table(name = "Servicerequest")
public class Servicerequest {
    private long id;
    private long principal;
    private long location;
    private long aviationtype;
    private long myaircraft;
    private long paymethod;
    private double guarantee;
    private double amount;
    private Date dcreate;
    private Date dupdate;
    private Date dexpired;
    private Date dlanding;
    private Date rdate;
    private boolean released;
    private boolean ticket;
    private boolean closed;
    private String serialcode;
    private long incomingloc; //ALTER TABLE JD_Fussy.dbo.Servicerequest ADD incomingloc BIGINT NULL;

    private Set<Itemrequest> items;

    public Servicerequest() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SERVICEREQUEST_ID", unique = true, nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "PRINCIPAL_ID")
    public long getPrincipal() {
        return principal;
    }

    public void setPrincipal(long principal) {
        this.principal = principal;
    }

    @Column(name = "MYAIRCRAFT_ID")
    public long getMyaircraft() {
        return myaircraft;
    }

    public void setMyaircraft(long myaircraft) {
        this.myaircraft = myaircraft;
    }

    public long getLocation() {
        return location;
    }

    public void setLocation(long location) {
        this.location = location;
    }

    public long getAviationtype() {
        return aviationtype;
    }

    public void setAviationtype(long aviationtype) {
        this.aviationtype = aviationtype;
    }

    public long getPaymethod() {
        return paymethod;
    }

    public void setPaymethod(long paymethod) {
        this.paymethod = paymethod;
    }

    public double getGuarantee() {
        return guarantee;
    }

    public void setGuarantee(double guarantee) {
        this.guarantee = guarantee;
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

    public Date getDexpired() {
        return dexpired;
    }

    public void setDexpired(Date dexpired) {
        this.dexpired = dexpired;
    }

    public Date getDlanding() {
        return dlanding;
    }

    public void setDlanding(Date dlanding) {
        this.dlanding = dlanding;
    }

    public boolean isReleased() {
        return released;
    }

    public void setReleased(boolean released) {
        this.released = released;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public boolean isTicket() {
        return ticket;
    }

    public void setTicket(boolean ticket) {
        this.ticket = ticket;
    }

    public Date getRdate() {
        return rdate;
    }

    public void setRdate(Date rdate) {
        this.rdate = rdate;
    }

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "Servicerequest_Itemrequest", joinColumns = {
            @JoinColumn(name = "SERVICEREQUEST_ID", nullable = false, updatable = false) },
            inverseJoinColumns = {@JoinColumn(name = "ITEMREQUEST_ID",
                    nullable = false, updatable = false) })
    public Set<Itemrequest> getItems() {
        return items;
    }

    public void setItems(Set<Itemrequest> items) {
        this.items = items;
    }

    public String getSerialcode() {
        return serialcode;
    }

    public void setSerialcode(String serialcode) {
        this.serialcode = serialcode;
    }

    public long getIncomingloc() {
        return incomingloc;
    }

    public void setIncomingloc(long incomingloc) {
        this.incomingloc = incomingloc;
    }
}
