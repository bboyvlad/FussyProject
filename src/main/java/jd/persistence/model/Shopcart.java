package jd.persistence.model;

import javax.persistence.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by eduardom on 9/12/16.
 */
@Entity
@Table(name = "Shopcart")
public class Shopcart {
    private Long id;
    private String name;
    private long location;
    private long captain;
    private double amount;
    private long aviationtype;
    private Date dcreate;
    private Date dupdate;
    private Date dlanding;
    private Date rdate; //Return date
    private long myaircraft;
    private Set<Itemcart> items = new HashSet<Itemcart>(0);

    public Shopcart() {
    }

    public Shopcart(Long id, String name, long location, double amount, long aviationtype, Date dcreate, Date dupdate, Date dlanding, long myaircraft, Set<Itemcart> items) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.amount = amount;
        this.aviationtype = aviationtype;
        this.dcreate = dcreate;
        this.dupdate = dupdate;
        this.dlanding = dlanding;
        this.myaircraft = myaircraft;
        this.items = items;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SHOPCART_ID", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Column(name = "MYCAPTAIN_ID")
    public long getCaptain() {
        return captain;
    }

    public void setCaptain(long captain) {
        this.captain = captain;
    }

    public Date getRdate() {
        return rdate;
    }

    public void setRdate(Date rdate) {
        this.rdate = rdate;
    }

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "Item_Shopcart", joinColumns = {
            @JoinColumn(name = "SHOPCART_ID", nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "ITEMCART_ID",
                    nullable = false, updatable = false) })
    public Set<Itemcart> getItems() {
        return items;
    }

    public void setItems(Set<Itemcart> items) {
        this.items = items;
    }

    @Column(name = "MYAIRCRAFT_ID")
    public long getMyaircraft() {
        return myaircraft;
    }

    public void setMyaircraft(long myaircraft) {
        this.myaircraft = myaircraft;
    }
}
