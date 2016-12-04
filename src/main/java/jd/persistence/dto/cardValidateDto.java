package jd.persistence.dto;

/**
 * Created by eduardom on 10/24/16.
 */
public class cardValidateDto {

    private String fuelCardCode;
    private String estatus;
    private String cardCode;
    private String cardName;
    private String aircraftCode;

    public cardValidateDto() {
    }

    public String getFuelCardCode() {
        return fuelCardCode;
    }

    public void setFuelCardCode(String fuelCardCode) {
        this.fuelCardCode = fuelCardCode;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getAircraftCode() {
        return aircraftCode;
    }

    public void setAircraftCode(String aircraftCode) {
        this.aircraftCode = aircraftCode;
    }
}
