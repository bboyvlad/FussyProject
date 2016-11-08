package jd.persistence.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by eduardom on 10/3/16.
 */
@Entity
@Table(name = "Giftcard")
public class Giftcard {
    private long id;
    private String title;
    private String claimcode;
    private Double amount;
    private Date dcreate;
    private Date dupdate;
    private boolean registered;
    private String recipient_id;
    private String recipient_email;
    private String recipient_name;
    private String recipientmessage;


    public Giftcard() {
    }

    public Giftcard(long id, String title, String claimcode, Double amount, Date dcreate, Date dupdate, boolean registered, String recipient_id, String recipient_email, String recipient_name, String recipientmessage) {
        this.id = id;
        this.title = title;
        this.claimcode = claimcode;
        this.amount = amount;
        this.dcreate = dcreate;
        this.dupdate = dupdate;
        this.registered = false;
        this.recipient_id = recipient_id;
        this.recipient_email = recipient_email;
        this.recipient_name = recipient_name;
        this.recipientmessage = recipientmessage;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GIFTCARD_ID", unique = true, nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getClaimcode() {
        return claimcode;
    }

    public void setClaimcode(String claimcode) {
        this.claimcode = claimcode;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getDcreate() {
        return dcreate;
    }

    public void setDcreate(Date dcreate) {
        this.dcreate = dcreate;
    }

    public Date getDupdate() {
        return dupdate;
    }

    public void setDupdate(Date dupdate) {
        this.dupdate = dupdate;
    }

    public boolean isRegistered() {
        return registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }

    @Column(name = "recipientid")
    public String getRecipient_id() {
        return recipient_id;
    }

    public void setRecipient_id(String recipient_id) {
        this.recipient_id = recipient_id;
    }

    @Column(name = "recipientemail")
    public String getRecipient_email() {
        return recipient_email;
    }

    public void setRecipient_email(String recipient_email) {
        this.recipient_email = recipient_email;
    }

    @Column(name = "recipientname")
    public String getRecipient_name() {
        return recipient_name;
    }

    public void setRecipient_name(String recipient_name) {
        this.recipient_name = recipient_name;
    }

    public String getRecipientmessage() {
        return recipientmessage;
    }

    public void setRecipientmessage(String recipientmessage) {
        this.recipientmessage = recipientmessage;
    }
}
