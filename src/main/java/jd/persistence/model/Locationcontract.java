package jd.persistence.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by eduardom on 12/9/16.
 */
@Entity
@Table(name = "Locationcontract")
public class Locationcontract {

    private long id;
    private long location;
    private Set<Providercontract> contracts = new HashSet<Providercontract>(0);
    private Set<Fee> fees = new HashSet<Fee>(0);
    private boolean active;

    public Locationcontract() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LOCATIONCONTRACT_ID", unique = true, nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getLocation() {
        return location;
    }

    public void setLocation(long location) {
        this.location = location;
    }

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "Provider_Location", joinColumns = {
            @JoinColumn(name = "LOCATIONCONTRACT_ID", nullable = false, updatable = false) },
            inverseJoinColumns = {@JoinColumn(name = "PROVIDERCONTRACT_ID",
                    nullable = false, updatable = false) })
    public Set<Providercontract> getContracts() {
        return contracts;
    }

    public void setContracts(Set<Providercontract> contracts) {
        this.contracts = contracts;
    }

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "Location_Fee", joinColumns = {
            @JoinColumn(name = "LOCATIONCONTRACT_ID", nullable = false, updatable = false) },
            inverseJoinColumns = {@JoinColumn(name = "FEE_ID",
                    nullable = false, updatable = false) })
    public Set<Fee> getFees() {
        return fees;
    }

    public void setFees(Set<Fee> fees) {
        this.fees = fees;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
