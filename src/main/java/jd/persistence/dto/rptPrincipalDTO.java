package jd.persistence.dto;

/**
 * Created by eduardom on 10/16/16.
 */
public class rptPrincipalDTO {

    private long id;
    private String name;
    private String lastname;

    public rptPrincipalDTO() {
    }

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

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
