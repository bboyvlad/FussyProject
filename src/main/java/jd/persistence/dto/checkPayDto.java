package jd.persistence.dto;

/**
 * Created by eduardom on 11/3/16.
 */
public class checkPayDto {
    private long payment;
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
}
