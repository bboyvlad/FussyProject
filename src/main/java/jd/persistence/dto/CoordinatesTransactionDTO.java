package jd.persistence.dto;

/**
 * Created by eduardom on 9/12/16.
 */
public class CoordinatesTransactionDTO {

    private String posicionuno;
    private String valorduno;
    private String posiciondos;
    private String valordos;
    private String serial;

    public CoordinatesTransactionDTO() {  }

    public CoordinatesTransactionDTO(String posicionuno, String valorduno, String posiciondos, String valordos, String serial) {
        this.posicionuno = posicionuno;
        this.valorduno = valorduno;
        this.posiciondos = posiciondos;
        this.valordos = valordos;
        this.serial = serial;
    }

    public String getPosicionuno() {
        return posicionuno;
    }

    public void setPosicionuno(String posicionuno) {
        this.posicionuno = posicionuno;
    }

    public String getValorduno() {
        return valorduno;
    }

    public void setValorduno(String valorduno) {
        this.valorduno = valorduno;
    }

    public String getPosiciondos() {
        return posiciondos;
    }

    public void setPosiciondos(String posiciondos) {
        this.posiciondos = posiciondos;
    }

    public String getValordos() {
        return valordos;
    }

    public void setValordos(String valordos) {
        this.valordos = valordos;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }
}
