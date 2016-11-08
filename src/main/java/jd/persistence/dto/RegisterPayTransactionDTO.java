package jd.persistence.dto;

import java.util.Date;

/**
 * Created by eduardom on 9/6/16.
 */
public class RegisterPayTransactionDTO {


    private String transnumber;
    private double amount;
    private String bankname;
    private Date operationDate;
    private String currency;
    private String sourcetoken;
    private String description;
    private String type;
    private String cardname; //jdcard
    private Long idpaymethod;
    private String pay_ccsec;

    public RegisterPayTransactionDTO() {
    }

    public RegisterPayTransactionDTO(String transnumber, double amount, String bankname, Date operationDate, String currency, String sourcetoken, String description, String type, String cardname, Long idpaymethod, String pay_ccsec) {
        this.transnumber = transnumber;
        this.amount = amount;
        this.bankname = bankname;
        this.operationDate = operationDate;
        this.currency = currency;
        this.sourcetoken = sourcetoken;
        this.description = description;
        this.type = type;
        this.cardname = cardname;
        this.idpaymethod = idpaymethod;
        this.pay_ccsec = pay_ccsec;
    }

    public String getTransNumber() {
        return transnumber;
    }

    public void setTransNumber(String transNumber) {
        this.transnumber = transNumber;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getBankName() {
        return bankname;
    }

    public void setBankName(String bankname) {
        this.bankname = bankname;
    }

    public Date getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(Date operationDate) {
        this.operationDate = operationDate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTransnumber() {
        return transnumber;
    }

    public void setTransnumber(String transnumber) {
        this.transnumber = transnumber;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getSourcetoken() {
        return sourcetoken;
    }

    public void setSourcetoken(String sourcetoken) {
        this.sourcetoken = sourcetoken;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCardname() {
        return cardname;
    }

    public void setCardname(String cardname) {
        this.cardname = cardname;
    }

    public Long getIdpaymethod() {
        return idpaymethod;
    }

    public void setIdpaymethod(Long idpaymethod) {
        this.idpaymethod = idpaymethod;
    }

    public String getPay_ccsec() {
        return pay_ccsec;
    }

    public void setPay_ccsec(String pay_ccsec) {
        this.pay_ccsec = pay_ccsec;
    }
}
