package jd.persistence.dto;

import java.util.ArrayList;

/**
 * Created by eduardom on 11/22/16.
 */
public class newProductDto {
    private String productname;
    private String detail;
    private String productunit;
    private String productunitdesc;
    private boolean active;
    private String pricetype;
    private ArrayList<newPriceDto> prices;

    public newProductDto() {
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getProductunit() {
        return productunit;
    }

    public void setProductunit(String productunit) {
        this.productunit = productunit;
    }

    public String getProductunitdesc() {
        return productunitdesc;
    }

    public void setProductunitdesc(String productunitdesc) {
        this.productunitdesc = productunitdesc;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public ArrayList<newPriceDto> getPrices() {
        return prices;
    }

    public void setPrices(ArrayList<newPriceDto> prices) {
        this.prices = prices;
    }

    public String getPricetype() {
        return pricetype;
    }

    public void setPricetype(String pricetype) {
        this.pricetype = pricetype;
    }
}
