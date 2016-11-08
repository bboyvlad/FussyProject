package jd.persistence.dto;

import java.util.Date;

/**
 * Created by eduardom on 11/4/16.
 */
public class dateTransactionPayDto {
    private long paymethod;
    private Date fromdate;
    private Date todate;

    public dateTransactionPayDto() {
    }

    public long getPaymethod() {
        return paymethod;
    }

    public void setPaymethod(long paymethod) {
        this.paymethod = paymethod;
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
