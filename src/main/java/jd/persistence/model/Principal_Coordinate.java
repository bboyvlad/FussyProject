package jd.persistence.model;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by eduardom on 9/10/16.
 */
@Entity
@Table(name = "Principal_Coordinate")
public class Principal_Coordinate {

    private Long id;
    private String coordinate;
    private String serial;
    private Date dcreate;
    private Date dupdate;
    private boolean active;

    public Principal_Coordinate() {
        super();
    }

    public Principal_Coordinate(Long id, String coordinate, String serial, Date dcreate, Date dupdate, boolean active) {
        this.id = id;
        this.coordinate = coordinate;
        this.serial = serial;
        this.dcreate = dcreate;
        this.dupdate = dupdate;
        this.active = active;
    }

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COORDINATE_ID", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

}
