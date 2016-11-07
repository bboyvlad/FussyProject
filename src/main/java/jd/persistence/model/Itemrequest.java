package jd.persistence.model;

import javax.persistence.*;

/**
 * Created by eduardom on 10/8/16.
 */
@Entity
@Table(name = "Itemrequest")
public class Itemrequest {

    private long itemid;
    private long provider;
    private long catalog;
    private long product;
    private long quantity;
    private double unitprice;
    private double totalprice;
    private String pricetype;
    private String pricename;
    private String pricedesc;
    private boolean checked;

    public Itemrequest() {
    }

    public Itemrequest(long itemid, long provider, long catalog, long product, long quantity, double unitprice, double totalprice, String pricetype, String pricename, String pricedesc, boolean checked) {
        this.itemid = itemid;
        this.provider = provider;
        this.catalog = catalog;
        this.product = product;
        this.quantity = quantity;
        this.unitprice = unitprice;
        this.totalprice = totalprice;
        this.pricetype = pricetype;
        this.pricename = pricename;
        this.pricedesc = pricedesc;
        this.checked = checked;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ITEMREQUEST_ID", unique = true, nullable = false)
    public long getItemid() {
        return itemid;
    }

    public void setItemid(long itemid) {
        this.itemid = itemid;
    }

    public long getProvider() {
        return provider;
    }

    public void setProvider(long provider) {
        this.provider = provider;
    }

    public long getCatalog() {
        return catalog;
    }

    public void setCatalog(long catalog) {
        this.catalog = catalog;
    }

    public long getProduct() {
        return product;
    }

    public void setProduct(long product) {
        this.product = product;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
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

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
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
