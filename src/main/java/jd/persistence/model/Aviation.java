package jd.persistence.model;

import javax.persistence.*;

/**
 * Created by eduardom on 10/10/16.
 */
@Entity
@Table(name = "Aviation")
public class Aviation {
    private long id;
    private String name;
    private String detaildesc;
    private boolean active;

    public Aviation() {
    }

    public Aviation(long id, String name, String detaildesc, boolean active) {
        this.id = id;
        this.name = name;
        this.detaildesc = detaildesc;
        this.active = true;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AVIATION_ID")
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

    public String getDetaildesc() {
        return detaildesc;
    }

    public void setDetaildesc(String detaildesc) {
        this.detaildesc = detaildesc;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
