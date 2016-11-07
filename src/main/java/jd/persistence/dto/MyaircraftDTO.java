package jd.persistence.dto;

/**
 * Created by eduardom on 10/9/16.
 */
public class MyaircraftDTO {

    private long id;
    private String tailnumber;
    private long aircrafttype;
    private String name;
    private int aviationtype;
    private boolean active;

    public MyaircraftDTO() {
    }

    public MyaircraftDTO(long id, String tailnumber, long aircrafttype, String name, int aviationtype, boolean active) {
        this.id = id;
        this.tailnumber = tailnumber;
        this.aircrafttype = aircrafttype;
        this.name = name;
        this.aviationtype = aviationtype;
        this.active = active;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTailnumber() {
        return tailnumber;
    }

    public void setTailnumber(String tailnumber) {
        this.tailnumber = tailnumber;
    }

    public long getAircrafttype() {
        return aircrafttype;
    }

    public void setAircrafttype(long aircrafttype) {
        this.aircrafttype = aircrafttype;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAviationtype() {
        return aviationtype;
    }

    public void setAviationtype(int aviationtype) {
        this.aviationtype = aviationtype;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
