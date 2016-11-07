package jd.persistence.dto;

import org.springframework.transaction.annotation.Transactional;

/**
 * Created by eduardom on 9/27/16.
 */
public class CoordinatesDTO {
    private String crdOne;
    private String valOne;
    private String crdTwo;
    private String valTwo;
    private String serialcrd;

    public String getCrdOne() {
        return crdOne;
    }

    public void setCrdOne(String crdOne) {
        this.crdOne = crdOne;
    }

    public String getValOne() {
        return valOne;
    }

    public void setValOne(String valOne) {
        this.valOne = valOne;
    }

    public String getCrdTwo() {
        return crdTwo;
    }

    public void setCrdTwo(String crdTwo) {
        this.crdTwo = crdTwo;
    }

    public String getValTwo() {
        return valTwo;
    }

    public void setValTwo(String valTwo) {
        this.valTwo = valTwo;
    }

    public String getSerialcrd() {
        return serialcrd;
    }

    public void setSerialcrd(String serialcrd) {
        this.serialcrd = serialcrd;
    }

    @Override
    public String toString() {
        return "CoordinatesDTO{" +
                "crdOne='" + crdOne + '\'' +
                ", valOne='" + valOne + '\'' +
                ", crdTwo='" + crdTwo + '\'' +
                ", valTwo='" + valTwo + '\'' +
                ", serialcrd='" + serialcrd + '\'' +
                '}';
    }
}
