package jd.persistence.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by eduardom on 10/2/16.
 */
@Entity
@Table(name = "Itemcart")
public class Itemcart {

    private long id;
    private String pricetype;
    private String pricename;
    private String pricedesc;
    private long product;
    private int quantity;
    private double unitprice;
    private double totalprice;

    public Itemcart() {
    }

    public Itemcart(long id, String pricetype, String pricename, String pricedesc, long product, int quantity, double unitprice, double totalprice) {
        this.id = id;
        this.pricetype = pricetype;
        this.pricename = pricename;
        this.pricedesc = pricedesc;
        this.product = product;
        this.quantity = quantity;
        this.unitprice = unitprice;
        this.totalprice = totalprice;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ITEMCART_ID", unique = true, nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProduct() {
        return product;
    }

    public void setProduct(long product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitprice() {
        return unitprice;
    }

    public void setUnitprice(double unitprice) {
        this.unitprice = unitprice;
    }

    public double getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(double totalprice) {
        this.totalprice = totalprice;
    }

    public String getPricetype() {
        return pricetype;
    }

    public void setPricetype(String pricetype) {
        this.pricetype = pricetype;
    }

    public String getPricename() {
        return pricename;
    }

    public void setPricename(String pricename) {
        this.pricename = pricename;
    }

    public String getPricedesc() {
        return pricedesc;
    }

    public void setPricedesc(String pricedesc) {
        this.pricedesc = pricedesc;
    }
}
