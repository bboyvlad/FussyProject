package jd.persistence.dto;

/**
 * Created by eduardom on 11/14/16.
 */
public class rptServiceItemTicketDto {

    private boolean feenabled;
    private String description;
    private Double amount;
    private String pricedesc;

    public rptServiceItemTicketDto() {
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

    public boolean isFeenabled() {
        return feenabled;
    }

    public void setFeenabled(boolean feenabled) {
        this.feenabled = feenabled;
    }
}
