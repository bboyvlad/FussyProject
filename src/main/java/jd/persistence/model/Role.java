package jd.persistence.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;

/**
 * Created by eduardom on 10/15/16.
 */
@Entity
@Table(name = "Role")
public class Role {

    private long id;
    private String code;
    private String label;

    public Role() {
    }

    @Id
    @Column(name = "ROLE_ID", unique = true, nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    @NotNull
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @NotNull
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }


}
