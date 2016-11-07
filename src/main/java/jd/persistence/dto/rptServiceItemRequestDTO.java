package jd.persistence.dto;

/**
 * Created by eduardom on 10/18/16.
 */
public class rptServiceItemRequestDTO {

    private String description;
    private Double amount;
    private String pricedesc;

    public rptServiceItemRequestDTO() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getPricedesc() {
        return pricedesc;
    }

    public void setPricedesc(String pricedesc) {
        this.pricedesc = pricedesc;
    }
}
