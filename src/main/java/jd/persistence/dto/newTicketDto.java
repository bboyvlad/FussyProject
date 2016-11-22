package jd.persistence.dto;

import jd.persistence.model.Itemrequest;

import java.util.Set;

/**
 * Created by eduardom on 11/13/16.
 */
public class newTicketDto {
    private long servicerequest;
    private String ticket;
    private Set<Itemrequest> items;

    public newTicketDto() {
    }

    public long getServicerequest() {
        return servicerequest;
    }

    public void setServicerequest(long servicerequest) {
        this.servicerequest = servicerequest;
    }

    public Set<Itemrequest> getItems() {
        return items;
    }

    public void setItems(Set<Itemrequest> items) {
        this.items = items;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }
}
