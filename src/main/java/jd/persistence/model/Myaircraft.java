package jd.persistence.model;

import javax.persistence.*;

/**
 * Created by eduardom on 10/9/16.
 */
@Entity
@Table(name = "Myaircraft")
public class Myaircraft {

    private long id;
    private long aircraftype;
    private int aviationtype;
    private String name;
    private String model;
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
    private int craftlength;
    private int craftwidth;
    private int appspeed;
    private int wingspan;
    private int tailheight;
    private String arc;
    private boolean active;
    private String tailnumber;
    private boolean deleted;

    public Myaircraft() {
    }

    public Myaircraft(long id, int aviationtype, String name, String model, String craftype, String series, String engines, String units, int mrmp, int mtow, int owe, int mzfw, int mlgw, int mtank, int craftlength, int craftwidth, int appspeed, int wingspan, int tailheight, String arc, boolean active, String tailnumber, boolean deleted) {
        this.id = id;
        this.aviationtype = aviationtype;
        this.name = name;
        this.model = model;
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
        this.craftlength = craftlength;
        this.craftwidth = craftwidth;
        this.appspeed = appspeed;
        this.wingspan = wingspan;
        this.tailheight = tailheight;
        this.arc = arc;
        this.active = true;
        this.tailnumber = tailnumber;
        this.deleted = false;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MYAIRCRAFT_ID", unique = true, nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "AIRCRAFTYPE_ID")
    public long getAircraftype() {
        return aircraftype;
    }

    public void setAircraftype(long aircraftype) {
        this.aircraftype = aircraftype;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getMtow() {
        return mtow;
    }

    public void setMtow(int mtow) {
        this.mtow = mtow;
    }

    public int getCraftlength() {
        return craftlength;
    }

    public void getCraftlength(int craftlength) {
        this.craftlength = craftlength;
    }

    public String getCraftype() {
        return craftype;
    }

    public void setCraftype(String craftype) {
        this.craftype = craftype;
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

    public String getTailnumber() {
        return tailnumber;
    }

    public void setTailnumber(String tailnumber) {
        this.tailnumber = tailnumber;
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

    public int getAviationtype() {
        return aviationtype;
    }

    public void setAviationtype(int aviationtype) {
        this.aviationtype = aviationtype;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
