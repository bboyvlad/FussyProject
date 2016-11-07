package jd.persistence.dto;

/**
 * Created by eduardom on 10/13/16.
 */
public class PrepareflightpriceDTO {
    private long id;
    private String pricetype;
    private String pricename;
    private String pricedesc;
    private double price;
    private String currency;

    public PrepareflightpriceDTO() {
    }

    public PrepareflightpriceDTO(long id, String pricetype, String pricename, String pricedesc, double price, String currency) {
        this.id = id;
        this.pricetype = pricetype;
        this.pricename = pricename;
        this.pricedesc = pricedesc;
        this.price = price;
        this.currency = currency;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPricedesc() {
        return pricedesc;
    }

    public void setPricedesc(String pricedesc) {
        this.pricedesc = pricedesc;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
