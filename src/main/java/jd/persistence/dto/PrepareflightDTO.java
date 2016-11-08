package jd.persistence.dto;

import java.util.Date;

/**
 * Created by eduardom on 10/13/16.
 */
public class PrepareflightDTO {

    private long location;
    private long myaircraft;
    private long captain;
    private Date landing;

    public PrepareflightDTO() {
    }

    public PrepareflightDTO(long location, long myaircraft, long captain, Date landing) {
        this.location = location;
        this.myaircraft = myaircraft;
        this.captain = captain;
        this.landing = landing;
    }

    public long getLocation() {
        return location;
    }

    public void setLocation(long location) {
        this.location = location;
    }

    public long getMyaircraft() {
        return myaircraft;
    }

    public void setMyaircraft(long myaircraft) {
        this.myaircraft = myaircraft;
    }

    public Date getLanding() {
        return landing;
    }

    public void setLanding(Date landing) {
        this.landing = landing;
    }

    public long getCaptain() {
        return captain;
    }

    public void setCaptain(long captain) {
        this.captain = captain;
    }
}
