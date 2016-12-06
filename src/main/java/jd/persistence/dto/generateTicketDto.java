package jd.persistence.dto;

import jd.persistence.model.Itemrequest;

import java.util.Date;
import java.util.Set;

/**
 * Created by eduardom on 11/11/16.
 */
public class generateTicketDto {
    private long servicerequest;
    private long principal;
    private long location;
    private String principalname;
    private String locationname;
    private Date dcreate;
    private Date dupdate;
    private Date dlanding;
    private String serialcode;
    private Set<Itemrequest> items;

    public generateTicketDto() {
    }

    public long getServicerequest() {
        return servicerequest;
    }

    public void setServicerequest(long servicerequest) {
        this.servicerequest = servicerequest;
    }

    public long getPrincipal() {
        return principal;
    }

    public void setPrincipal(long principal) {
        this.principal = principal;
    }

    public long getLocation() {
        return location;
    }

    public void setLocation(long location) {
        this.location = location;
    }

    public String getPrincipalname() {
        return principalname;
    }

    public void setPrincipalname(String principalname) {
        this.principalname = principalname;
    }

    public String getLocationname() {
        return locationname;
    }

    public void setLocationname(String locationname) {
        this.locationname = locationname;
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

    public Date getDlanding() {
        return dlanding;
    }

    public void setDlanding(Date dlanding) {
        this.dlanding = dlanding;
    }

    public String getSerialcode() {
        return serialcode;
    }

    public void setSerialcode(String serialcode) {
        this.serialcode = serialcode;
    }

    public Set<Itemrequest> getItems() {
        return items;
    }

    public void setItems(Set<Itemrequest> items) {
        this.items = items;
    }
}
