package jd.persistence.dto;


import jd.persistence.model.Tranpay;

import java.util.Set;

/**
 * Created by eduardom on 10/4/16.
 */
public class CreateGiftcardDTO {

    private long paymethod;
    private double amount;
    private String recipient_email;
    private String recipient_name;
    private String recipientmessage;
    private String paycardsec;

    public CreateGiftcardDTO() {
    }

    public CreateGiftcardDTO(long paymethod, double amount, String recipient_email, String recipient_name, String recipientmessage, String paycardsec) {
        this.paymethod = paymethod;
        this.amount = amount;
        this.recipient_email = recipient_email;
        this.recipient_name = recipient_name;
        this.recipientmessage = recipientmessage;
        this.paycardsec = paycardsec;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getRecipient_email() {
        return recipient_email;
    }

    public void setRecipient_email(String recipient_email) {
        this.recipient_email = recipient_email;
    }

    public String getRecipient_name() {
        return recipient_name;
    }

    public void setRecipient_name(String recipient_name) {
        this.recipient_name = recipient_name;
    }

    public long getPaymethod() {
        return paymethod;
    }

    public void setPaymethod(long paymethod) {
        this.paymethod = paymethod;
    }

    public String getPaycardsec() {
        return paycardsec;
    }

    public void setPaycardsec(String paycardsec) {
        this.paycardsec = paycardsec;
    }

    public String getRecipientmessage() {
        return recipientmessage;
    }

    public void setRecipientmessage(String recipientmessage) {
        this.recipientmessage = recipientmessage;
    }

}
