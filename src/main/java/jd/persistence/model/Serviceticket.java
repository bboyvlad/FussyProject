package jd.persistence.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Created by eduardom on 11/11/16.
 */
@Entity
@Table(name = "Serviceticket")
public class Serviceticket {

    private long id; //
    private long principal; //
    private long paymethod;
    private long location;
    private double amount;
    private long aviationtype;
    private Date dcreate;
    private Date dupdate;
    private Date dlanding;
    private boolean closed;
    private String serialcode;
    private String ticket;
    private Set<Itemticket> items;

    public Serviceticket() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SERVICETICKET_ID", unique = true, nullable = false)
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

    public long getPaymethod() {
        return paymethod;
    }

    public void setPaymethod(long paymethod) {
        this.paymethod = paymethod;
    }

    public long getLocation() {
        return location;
    }

    public void setLocation(long location) {
        this.location = location;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getAviationtype() {
        return aviationtype;
    }

    public void setAviationtype(long aviationtype) {
        this.aviationtype = aviationtype;
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

    public Date getDlanding() {
        return dlanding;
    }

    public void setDlanding(Date dlanding) {
        this.dlanding = dlanding;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public String getSerialcode() {
        return serialcode;
    }

    public void setSerialcode(String serialcode) {
        this.serialcode = serialcode;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "Serviceticket_Itemticket", joinColumns = {
            @JoinColumn(name = "SERVICETICKET_ID", nullable = false, updatable = false) },
            inverseJoinColumns = {@JoinColumn(name = "ITEMTICKET_ID",
                    nullable = false, updatable = false) })
    public Set<Itemticket> getItems() {
        return items;
    }

    public void setItems(Set<Itemticket> items) {
        this.items = items;
    }
}

