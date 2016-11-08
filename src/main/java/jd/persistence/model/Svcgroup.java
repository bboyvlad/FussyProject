package jd.persistence.model;

import javax.persistence.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by eduardom on 9/12/16.
 */
@Entity
@Table(name = "Svcgroup")
public class Svcgroup {
    private Long id;
    private String name;
    private String detaildesc;
    private Boolean active;
    private Set<Product> products = new HashSet<Product>(0);

    public Svcgroup() {
    }

    public Svcgroup(Long id, String name, String detaildesc, Boolean active, Set<Product> products) {
        this.id = id;
        this.name = name;
        this.detaildesc = detaildesc;
        this.active = active;
        this.products = products;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SVCGROUP_ID", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetaildesc() {
        return detaildesc;
    }

    public void setDetaildesc(String detaildesc) {
        this.detaildesc = detaildesc;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "Svcgroup_Product", joinColumns = {
            @JoinColumn(name = "SVCGROUP_ID", nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "PRODUCT_ID",
                    nullable = false, updatable = false) })
    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
}
