package jd.persistence.dto;

import jd.persistence.dto.ItemcartDTO;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by eduardom on 10/9/16.
 */
public class ItemcartDTO {

    private long id; //soruceid
    private String pricetype;
    private String pricename;
    private String pricedesc;
    private int quantity;
    private double price; //soruceprice
    private String currency;

    public ItemcartDTO() {
    }

    public ItemcartDTO(long id, String pricetype, String pricename, String pricedesc, int quantity, double price, String currency) {
        this.id = id;
        this.pricetype = pricetype;
        this.pricename = pricename;
        this.pricedesc = pricedesc;
        this.quantity = quantity;
        this.price = price;
        this.currency = currency;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
