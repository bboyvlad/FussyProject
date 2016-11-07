package jd.persistence.dto;

import java.util.Date;

/**
 * Created by eduardom on 9/30/16.
 */
public class PriceDTO {

    private long product_id;
    private Date fromdate;
    private Date todate;

    public PriceDTO() {}

    public long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(long product_id) {
        this.product_id = product_id;
    }

    public Date getFromdate() {
        return fromdate;
    }

    public void setFromdate(Date fromdate) {
        this.fromdate = fromdate;
    }

    public Date getTodate() {
        return todate;
    }

    public void setTodate(Date todate) {
        this.todate = todate;
    }
}
