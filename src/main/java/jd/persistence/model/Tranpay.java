package jd.persistence.model;

import com.stripe.model.Money;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by eduardom on 9/5/16.
 */

@Entity
@Table(name="Tranpay")
public class Tranpay {


    private Long tranid;
    private String trantype;
    private String transtatus;
    private double tranamount;
    private Date trandate;
    private Date tranupdate;
    private String trantoken;
    private String trandescription;


    public Tranpay() {
    }

    public Tranpay(Long tranid, String trantype, String transtatus, double tranamount, Date trandate, Date tranupdate, String trantoken) {
        this.tranid = tranid;
        this.trantype = trantype;
        this.transtatus = transtatus;
        this.tranamount = tranamount;
        this.trandate = trandate;
        this.tranupdate = tranupdate;
        this.trantoken = trantoken;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TRANPAY_ID", unique = true, nullable = false)
    public Long getTranid() {
        return tranid;
    }

    public void setTranid(Long tranid) {
        this.tranid = tranid;
    }

    public String getTrantype() {
        return trantype;
    }

    public void setTrantype(String trantype) {
        this.trantype = trantype;
    }

    public String getTranstatus() {
        return transtatus;
    }

    public void setTranstatus(String transtatus) {
        this.transtatus = transtatus;
    }

    public double getTranamount() {
        return tranamount;
    }

    @Column(name="tranamount", columnDefinition="Decimal(10,2) default '0.00'")
    public void setTranamount(double tranamount) {
        this.tranamount = tranamount;
    }

    public Date getTrandate() {
        return trandate;
    }

    public void setTrandate(Date trandate) {
        this.trandate = trandate;
    }

    public Date getTranupdate() {
        return tranupdate;
    }

    public void setTranupdate(Date tranupdate) {
        this.tranupdate = tranupdate;
    }

    public String getTrantoken() {
        return trantoken;
    }

    public void setTrantoken(String trantoken) {
        this.trantoken = trantoken;
    }

    public String getTrandescription() {
        return trandescription;
    }

    public void setTrandescription(String trandescription) {
        this.trandescription = trandescription;
    }
}
