package jd.persistence.dto;

import jd.persistence.model.Itemcart;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by eduardom on 10/9/16.
 */
public class ShopcartDTO {



    private long location;
    private long myaircraft;
    private long captain;
    private Date landing;
    private Date returndate;
    private String description;
    private boolean generated;
    private Set<ItemcartDTO> items = new HashSet<ItemcartDTO>(0);

    public ShopcartDTO() {
    }

    public ShopcartDTO(long location, long myaircraft, long captain, Date landing, Date returndate, String description, boolean generated, Set<ItemcartDTO> items) {
        this.location = location;
        this.myaircraft = myaircraft;
        this.captain = captain;
        this.landing = landing;
        this.returndate = returndate;
        this.description = description;
        this.generated = false;
        this.items = items;
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

    public long getCaptain() {
        return captain;
    }

    public void setCaptain(long captain) {
        this.captain = captain;
    }

    public Date getLanding() {
        return landing;
    }

    public void setLanding(Date landing) {
        this.landing = landing;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isGenerated() {
        return generated;
    }

    public void setGenerated(boolean generated) {
        this.generated = generated;
    }

    public Set<ItemcartDTO> getItems() {
        return items;
    }

    public void setItems(Set<ItemcartDTO> items) {
        this.items = items;
    }

    public Date getReturndate() {
        return returndate;
    }

    public void setReturndate(Date returndate) {
        this.returndate = returndate;
    }
}
