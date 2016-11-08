package jd.persistence.model;

import javax.persistence.*;

/**
 * Created by eduardom on 10/9/16.
 */
@Entity
@Table(name = "Aircraftype")
public class Aircraftype {
    private long id;
    private String name;
    private String manufacture;
    private String craftype;
    private String series;
    private String engines;
    private String units;
    private int mrmp;
    private int mtow;
    private int owe;
    private int mzfw;
    private int mlgw;
    private int mtank;
    private String icao;
    private String iata;
    private int craftlength;
    private int craftwidth;
    private int appspeed;
    private int wingspan;
    private int tailheight;
    private String arc;
    private boolean active;

    public Aircraftype() {
    }

    public Aircraftype(long id, String name, String manufacture, String craftype, String series, String engines, String units, int mrmp, int mtow, int owe, int mzfw, int mlgw, int mtank, String icao, String iata, int craftlength, int craftwidth, int appspeed, int wingspan, int tailheight, String arc, boolean active) {
        this.id = id;
        this.name = name;
        this.manufacture = manufacture;
        this.craftype = craftype;
        this.series = series;
        this.engines = engines;
        this.units = units;
        this.mrmp = mrmp;
        this.mtow = mtow;
        this.owe = owe;
        this.mzfw = mzfw;
        this.mlgw = mlgw;
        this.mtank = mtank;
        this.icao = icao;
        this.iata = iata;
        this.craftlength = craftlength;
        this.craftwidth = craftwidth;
        this.appspeed = appspeed;
        this.wingspan = wingspan;
        this.tailheight = tailheight;
        this.arc = arc;
        this.active = active;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AIRCRAFTYPE_ID", unique = true, nullable = false)
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

    public String getManufacture() {
        return manufacture;
    }

    public void setManufacture(String manufacture) {
        this.manufacture = manufacture;
    }

    public String getCraftype() {
        return craftype;
    }

    public void setCraftype(String craftype) {
        this.craftype = craftype;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getEngines() {
        return engines;
    }

    public void setEngines(String engines) {
        this.engines = engines;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public int getMrmp() {
        return mrmp;
    }

    public void setMrmp(int mrmp) {
        this.mrmp = mrmp;
    }

    public int getMtow() {
        return mtow;
    }

    public void setMtow(int mtow) {
        this.mtow = mtow;
    }

    public int getOwe() {
        return owe;
    }

    public void setOwe(int owe) {
        this.owe = owe;
    }

    public int getMzfw() {
        return mzfw;
    }

    public void setMzfw(int mzfw) {
        this.mzfw = mzfw;
    }

    public int getMlgw() {
        return mlgw;
    }

    public void setMlgw(int mlgw) {
        this.mlgw = mlgw;
    }

    public int getMtank() {
        return mtank;
    }

    public void setMtank(int mtank) {
        this.mtank = mtank;
    }

    public String getIcao() {
        return icao;
    }

    public void setIcao(String icao) {
        this.icao = icao;
    }

    public String getIata() {
        return iata;
    }

    public void setIata(String iata) {
        this.iata = iata;
    }

    public int getCraftlength() {
        return craftlength;
    }

    public void setCraftlength(int craftlength) {
        this.craftlength = craftlength;
    }

    public int getCraftwidth() {
        return craftwidth;
    }

    public void setCraftwidth(int craftwidth) {
        this.craftwidth = craftwidth;
    }

    public int getAppspeed() {
        return appspeed;
    }

    public void setAppspeed(int appspeed) {
        this.appspeed = appspeed;
    }

    public int getWingspan() {
        return wingspan;
    }

    public void setWingspan(int wingspan) {
        this.wingspan = wingspan;
    }

    public int getTailheight() {
        return tailheight;
    }

    public void setTailheight(int tailheight) {
        this.tailheight = tailheight;
    }

    public String getArc() {
        return arc;
    }

    public void setArc(String arc) {
        this.arc = arc;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
