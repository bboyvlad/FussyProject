package jd.persistence.model;

import javax.persistence.*;

/**
 * Created by eduardom on 11/24/16.
 */
@Entity
@Table(name = "Provider")
public class Provider {

    private long id;
    private String name;
    private int prvtype;
    private int pricelist;
    private String contact;
    private String phone;
    private String email;
    private String operemail;
    private String adminemail;
    private String address;
    private String notes;
    private String cardcode;

    public Provider() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROVIDER_ID", unique = true, nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrvtype() {
        return prvtype;
    }

    public void setPrvtype(int prvtype) {
        this.prvtype = prvtype;
    }

    public int getPricelist() {
        return pricelist;
    }

    public void setPricelist(int pricelist) {
        this.pricelist = pricelist;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOperemail() {
        return operemail;
    }

    public void setOperemail(String operemail) {
        this.operemail = operemail;
    }

    public String getAdminemail() {
        return adminemail;
    }

    public void setAdminemail(String adminemail) {
        this.adminemail = adminemail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Column(name = "CardCode")
    public String getCardcode() {
        return cardcode;
    }

    public void setCardcode(String cardcode) {
        this.cardcode = cardcode;
    }
}
