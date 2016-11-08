package jd.persistence.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by eduardom on 10/9/16.
 */
@Entity
@Table(name = "Mycaptain")
public class Mycaptain {
    private long id;
    private String name;
    private String license;
    private Date dateofbirth;
    private String address;
    private String city;
    private String country;
    private String phone;
    private String email;
    private boolean active;
    private boolean deleted;

    public Mycaptain() {
    }

    public Mycaptain(long id, String name, String license, Date dateofbirth, String address, String city, String country, String phone, String email, boolean active, boolean deleted) {
        this.id = id;
        this.name = name;
        this.license = license;
        this.dateofbirth = dateofbirth;
        this.address = address;
        this.city = city;
        this.country = country;
        this.phone = phone;
        this.email = email;
        this.active = true;
        this.deleted = false;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MYCAPTAIN_ID", unique = true, nullable = false)
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

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public Date getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(Date dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
