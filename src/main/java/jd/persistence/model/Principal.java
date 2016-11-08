package jd.persistence.model;


import org.springframework.security.core.Authentication;

import javax.persistence.*;
import java.util.*;


/**
 * Created by eduardom on 9/3/16.
 */

@Entity
@Table(name="Principal", uniqueConstraints = {
        @UniqueConstraint(columnNames = "PRINCIPAL_ID")})
public class Principal implements java.security.Principal {

    private long id;
    private String name;
    private String lastname;
    private String email;
    private String password;
    private boolean enabled = true;
    private String cardcode;
    private boolean credentialsexpired=false;
    private boolean expired=false;
    private boolean locked=false;
    private Collection<Role> roles;
    private Set<Paymethod> payments = new HashSet<Paymethod>(0);
    private Set<Shopcart> cart = new HashSet<Shopcart>(0);
    private Set<Myaircraft> myaircrafts = new HashSet<Myaircraft>(0);
    private Set<Mycaptain> mycaptains = new HashSet<Mycaptain>(0);
    private Principal_Coordinate coordinates;

    public Principal() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRINCIPAL_ID", unique = true, nullable = false)
    public long getId() {
        return id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    @Column(name = "lastname")
    public String getLastname() {
        return lastname;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    @Column(name = "enabled")
    public boolean isEnabled() {
        return enabled;
    }

    @Column(name = "cardcode")
    public String getCardcode() {
        return cardcode;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public void setCardcode(String cardcode) {
        this.cardcode = cardcode;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "Principal_Paymethod", joinColumns = {
            @JoinColumn(name = "PRINCIPAL_ID", nullable = false, updatable = false) },
            inverseJoinColumns = {@JoinColumn(name = "PAYMETHOD_ID",
                    nullable = false, updatable = false) })
    public Set<Paymethod> getPayments() {
        return payments;
    }

    public void setPayments(Set<Paymethod> payments) {
        this.payments = payments;
    }

    @OneToOne(targetEntity = Principal_Coordinate.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(nullable =false, name = "PRINCIPAL_ID")
    public Principal_Coordinate getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Principal_Coordinate coordinates) {
        this.coordinates = coordinates;
    }

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval=true)
    @JoinTable(name = "Principal_Shopcart", joinColumns = {
            @JoinColumn(name = "PRINCIPAL_ID", nullable = false, updatable = false) },
            inverseJoinColumns = {@JoinColumn(name = "SHOPCART_ID",
                    nullable = false, updatable = false) })
    public Set<Shopcart> getCart() {
        return cart;
    }

    public void setCart(Set<Shopcart> cart) {
        this.cart = cart;
    }

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "Principal_Myaircraft", joinColumns = {
            @JoinColumn(name = "PRINCIPAL_ID", nullable = false, updatable = false) },
            inverseJoinColumns = {@JoinColumn(name = "MYAIRCRAFT_ID",
                    nullable = false, updatable = false) })
    public Set<Myaircraft> getMyaircrafts() {
        return myaircrafts;
    }

    public void setMyaircrafts(Set<Myaircraft> myaircrafts) {
        this.myaircrafts = myaircrafts;
    }

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "Principal_Mycaptain", joinColumns = {
            @JoinColumn(name = "PRINCIPAL_ID", nullable = false, updatable = false) },
            inverseJoinColumns = {@JoinColumn(name = "MYCAPTAIN_ID",
                    nullable = false, updatable = false) })
    public Set<Mycaptain> getMycaptains() {
        return mycaptains;
    }

    public void setMycaptains(Set<Mycaptain> mycaptains) {
        this.mycaptains = mycaptains;
    }

    public boolean isCredentialsexpired() {
        return credentialsexpired;
    }

    public void setCredentialsexpired(boolean credentialsexpired) {
        this.credentialsexpired = credentialsexpired;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    @ManyToMany(fetch = FetchType.EAGER,
                cascade = CascadeType.ALL)
    @JoinTable(name = "Principal_Role",
                joinColumns = @JoinColumn(name = "PRINCIPAL_ID",
                                referencedColumnName = "PRINCIPAL_ID"),
                inverseJoinColumns = @JoinColumn(name = "ROLE_ID",
                                referencedColumnName = "ROLE_ID"))
    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }
}
