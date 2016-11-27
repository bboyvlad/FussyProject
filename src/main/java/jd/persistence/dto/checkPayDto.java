package jd.persistence.dto;

/**
 * Created by eduardom on 11/3/16.
 */
public class checkPayDto {
    private long payment;
    private double famount;
    private boolean approved;

    public checkPayDto() {
    }

    public long getPayment() {
        return payment;
    }

    public void setPayment(long payment) {
        this.payment = payment;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public double getFamount() {
        return famount;
    }

    public void setFamount(double famount) {
        this.famount = famount;
    }
}
