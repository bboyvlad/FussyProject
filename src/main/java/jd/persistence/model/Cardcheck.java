package jd.persistence.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by eduardom on 10/24/16.
 */
@Entity
@Table(name = "cardcheck")
public class Cardcheck {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 16, max = 16)
    @Pattern(regexp = "(^$|[0-9]{16})")
    private String fuelCardCode;

    @NotNull
    @NotEmpty
    private String estatus;

    private String cardCode;
    private String cardName;
    private String aircraftCode;
    private String sagroup;
    private String sacurrency;
    private long sabalance;
    private String sacreditcondition;
    private int saNdays;
    private long saAccumulated;
    private int saQttinv;
    private String ltdgroup;
    private String ltdcurrency;
    private long ltdbalance;
    private String ltdcreditcondition;
    private int ltdNdays;
    private long ltdAccumulated;
    private int ltdQttinv;
    private String incgroup;
    private String inccurrency;
    private long incbalance;
    private String inccreditcondition;
    private int incNdays;
    private long incAccumulated;
    private int incQttinv;
    //

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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


    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardName() {
        return cardName;
    }


    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public String getAircraftCode() {
        return aircraftCode;
    }


    public void setAircraftCode(String aircraftCode) {
        this.aircraftCode = aircraftCode;
    }


    public String getSagroup() {
        return sagroup;
    }


    public void setSagroup(String sagroup) {
        this.sagroup = sagroup;
    }


    public String getSacurrency() {
        return sacurrency;
    }


    public void setSacurrency(String sacurrency) {
        this.sacurrency = sacurrency;
    }


    public long getSabalance() {
        return sabalance;
    }


    public void setSabalance(long sabalance) {
        this.sabalance = sabalance;
    }


    public String getSacreditcondition() {
        return sacreditcondition;
    }


    public void setSacreditcondition(String sacreditcondition) {
        this.sacreditcondition = sacreditcondition;
    }


    public int getSaNdays() {
        return saNdays;
    }


    public void setSaNdays(int saNdays) {
        this.saNdays = saNdays;
    }


    public long getSaAccumulated() {
        return saAccumulated;
    }


    public void setSaAccumulated(long saAccumulated) {
        this.saAccumulated = saAccumulated;
    }


    public int getSaQttinv() {
        return saQttinv;
    }


    public void setSaQttinv(int saQttinv) {
        this.saQttinv = saQttinv;
    }


    public String getLtdgroup() {
        return ltdgroup;
    }


    public void setLtdgroup(String ltdgroup) {
        this.ltdgroup = ltdgroup;
    }


    public String getLtdcurrency() {
        return ltdcurrency;
    }


    public void setLtdcurrency(String ltdcurrency) {
        this.ltdcurrency = ltdcurrency;
    }


    public long getLtdbalance() {
        return ltdbalance;
    }


    public void setLtdbalance(long ltdbalance) {
        this.ltdbalance = ltdbalance;
    }


    public String getLtdcreditcondition() {
        return ltdcreditcondition;
    }


    public void setLtdcreditcondition(String ltdcreditcondition) {
        this.ltdcreditcondition = ltdcreditcondition;
    }


    public int getLtdNdays() {
        return ltdNdays;
    }


    public void setLtdNdays(int ltdNdays) {
        this.ltdNdays = ltdNdays;
    }


    public long getLtdAccumulated() {
        return ltdAccumulated;
    }


    public void setLtdAccumulated(long ltdAccumulated) {
        this.ltdAccumulated = ltdAccumulated;
    }


    public int getLtdQttinv() {
        return ltdQttinv;
    }


    public void setLtdQttinv(int ltdQttinv) {
        this.ltdQttinv = ltdQttinv;
    }


    public String getIncgroup() {
        return incgroup;
    }


    public void setIncgroup(String incgroup) {
        this.incgroup = incgroup;
    }


    public String getInccurrency() {
        return inccurrency;
    }


    public void setInccurrency(String inccurrency) {
        this.inccurrency = inccurrency;
    }


    public long getIncbalance() {
        return incbalance;
    }


    public void setIncbalance(long incbalance) {
        this.incbalance = incbalance;
    }


    public String getInccreditcondition() {
        return inccreditcondition;
    }


    public void setInccreditcondition(String inccreditcondition) {
        this.inccreditcondition = inccreditcondition;
    }


    public int getIncNdays() {
        return incNdays;
    }


    public void setIncNdays(int incNdays) {
        this.incNdays = incNdays;
    }


    public long getIncAccumulated() {
        return incAccumulated;
    }


    public void setIncAccumulated(long incAccumulated) {
        this.incAccumulated = incAccumulated;
    }


    public int getIncQttinv() {
        return incQttinv;
    }


    public void setIncQttinv(int incQttinv) {
        this.incQttinv = incQttinv;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((fuelCardCode == null) ? 0 : fuelCardCode.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Cardcheck validation = (Cardcheck) obj;
        if (!fuelCardCode.equals(validation.fuelCardCode)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("validation[fuelCardCode=").append(fuelCardCode).append("]")
                .append("validation[Status=").append(estatus).append("]")
                .append("validation[cardCode=").append(cardCode).append("]")
                .append("validation[cardName=").append(cardName).append("]")
                .append("validation[aircraftCode=").append(aircraftCode).append("]")
                .append("validation[sagroup=").append(sagroup).append("]")
                .append("validation[sacurrency=").append(sacurrency).append("]")
                .append("validation[sabalance=").append(sabalance).append("]")
                .append("validation[sacreditcondition=").append(sacreditcondition).append("]")
                .append("validation[saNdays=").append(saNdays).append("]")
                .append("validation[saAccumulated=").append(saAccumulated).append("]")
                .append("validation[saQttinv=").append(saQttinv).append("]")
                .append("validation[ltdgroup=").append(ltdgroup).append("]")
                .append("validation[ltdcurrency=").append(ltdcurrency).append("]")
                .append("validation[ltdbalance=").append(ltdbalance).append("]")
                .append("validation[ltdcreditcondition=").append(ltdcreditcondition).append("]")
                .append("validation[ltdNdays=").append(ltdNdays).append("]")
                .append("validation[ltdAccumulated=").append(ltdAccumulated).append("]")
                .append("validation[ltdQttinv=").append(ltdQttinv).append("]")
                .append("validation[incgroup=").append(incgroup).append("]")
                .append("validation[inccurrency=").append(inccurrency).append("]")
                .append("validation[incbalance=").append(incbalance).append("]")
                .append("validation[inccreditcondition=").append(inccreditcondition).append("]")
                .append("validation[incNdays=").append(incNdays).append("]")
                .append("validation[incAccumulated=").append(incAccumulated).append("]")
                .append("validation[incQttinv=").append(incQttinv).append("]");
        return builder.toString();
    }


}
