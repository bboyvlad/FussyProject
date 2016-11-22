package jd.persistence.dto;

import com.auth0.jwt.internal.com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

/**
 * Created by eduardom on 11/11/16.
 */
public class showServicesRequestDto {

    private long servicerequest;
    private long principal;
    private String principalname;
    private String locationname;
    private Date dcreate;
    private Date dlanding;
    private Date rdate;
    private String serialcode;

    public showServicesRequestDto() {
    }

    public showServicesRequestDto(long servicerequest, String principalname, String locationname, Date dcreate, Date dlanding, String serialcode) {
        this.servicerequest = servicerequest;
        this.principalname = principalname;
        this.locationname = locationname;
        this.dcreate = dcreate;
        this.dlanding = dlanding;
        this.serialcode = serialcode;
    }

    public long getServicerequest() {
        return servicerequest;
    }

    public void setServicerequest(long servicerequest) {
        this.servicerequest = servicerequest;
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

    @JsonIgnore
    public long getPrincipal() {
        return principal;
    }

    public void setPrincipal(long principal) {
        this.principal = principal;
    }

    public Date getRdate() {
        return rdate;
    }

    public void setRdate(Date rdate) {
        this.rdate = rdate;
    }
}
