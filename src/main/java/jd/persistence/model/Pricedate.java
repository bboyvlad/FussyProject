package jd.persistence.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by eduardom on 10/13/16.
 */
@Entity
@Table(name = "Pricedate")
public class Pricedate {

    private long id;
    private long location;
    private long provider;
    private long product;
    private long aviation;
    private String name;
    private boolean prepaid;
    private Date fromdate;
    private Date todate;
    private int interval;
    private String currency;
    private String measure;
    private String unit;
    private String unitdesc;
    private double price1;
    private double price2;
    private double price3;
    private double cost1;
    private double cost2;
    private double cost3;
    private int diff;
    private int perc;
    private int margin;
    private int minimun;
    private int maximun;
    private boolean feesenable;
    private int locfee;
    private int intopfee;
    private int othfee;
    private String feename;
    private boolean expired;

    public Pricedate() {
    }

    public Pricedate(long id, long location, long provider, long product, long aviation, String name, boolean prepaid, Date fromdate, Date todate, int interval, String currency, String measure, String unit, String unitdesc, double price1, double price2, double price3, double cost1, double cost2, double cost3, int diff, int perc, int margin, int minimun, int maximun, boolean feesenable, int locfee, int intopfee, int othfee, String feename, boolean expired) {
        this.id = id;
        this.location = location;
        this.provider = provider;
        this.product = product;
        this.aviation = aviation;
        this.name = name;
        this.prepaid = prepaid;
        this.fromdate = fromdate;
        this.todate = todate;
        this.interval = interval;
        this.currency = currency;
        this.measure = measure;
        this.unit = unit;
        this.unitdesc = unitdesc;
        this.price1 = price1;
        this.price2 = price2;
        this.price3 = price3;
        this.cost1 = cost1;
        this.cost2 = cost2;
        this.cost3 = cost3;
        this.diff = diff;
        this.perc = perc;
        this.margin = margin;
        this.minimun = minimun;
        this.maximun = maximun;
        this.feesenable = feesenable;
        this.locfee = locfee;
        this.intopfee = intopfee;
        this.othfee = othfee;
        this.feename = feename;
        this.expired = expired;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRICEDATE_ID", unique = true, nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "LOCATION_ID")
    public long getLocation() {
        return location;
    }

    public void setLocation(long location) {
        this.location = location;
    }

    @Column(name = "PROVIDER_ID")
    public long getProvider() {
        return provider;
    }

    public void setProvider(long provider) {
        this.provider = provider;
    }

    @Column(name = "PRODUCT_ID")
    public long getProduct() {
        return product;
    }

    public void setProduct(long product) {
        this.product = product;
    }

    @Column(name = "AVIATION_ID")
    public long getAviation() {
        return aviation;
    }

    public void setAviation(long aviation) {
        this.aviation = aviation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPrepaid() {
        return prepaid;
    }

    public void setPrepaid(boolean prepaid) {
        this.prepaid = prepaid;
    }

    public Date getFromdate() {
        return fromdate;
    }

    public void setFromdate(Date fromdate) {
        this.fromdate = fromdate;
    }

    public Date getTodate() {
        return todate;
    }

    public void setTodate(Date todate) {
        this.todate = todate;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnitdesc() {
        return unitdesc;
    }

    public void setUnitdesc(String unitdesc) {
        this.unitdesc = unitdesc;
    }

    public double getPrice1() {
        return price1;
    }

    public void setPrice1(double price1) {
        this.price1 = price1;
    }

    public double getPrice2() {
        return price2;
    }

    public void setPrice2(double price2) {
        this.price2 = price2;
    }

    public double getPrice3() {
        return price3;
    }

    public void setPrice3(double price3) {
        this.price3 = price3;
    }

    public double getCost1() {
        return cost1;
    }

    public void setCost1(double cost1) {
        this.cost1 = cost1;
    }

    public double getCost2() {
        return cost2;
    }

    public void setCost2(double cost2) {
        this.cost2 = cost2;
    }

    public double getCost3() {
        return cost3;
    }

    public void setCost3(double cost3) {
        this.cost3 = cost3;
    }

    public int getDiff() {
        return diff;
    }

    public void setDiff(int diff) {
        this.diff = diff;
    }

    public int getPerc() {
        return perc;
    }

    public void setPerc(int perc) {
        this.perc = perc;
    }

    public int getMargin() {
        return margin;
    }

    public void setMargin(int margin) {
        this.margin = margin;
    }

    public int getMinimun() {
        return minimun;
    }

    public void setMinimun(int minimun) {
        this.minimun = minimun;
    }

    public int getMaximun() {
        return maximun;
    }

    public void setMaximun(int maximun) {
        this.maximun = maximun;
    }

    public boolean isFeesenable() {
        return feesenable;
    }

    public void setFeesenable(boolean feesenable) {
        this.feesenable = feesenable;
    }

    public int getLocfee() {
        return locfee;
    }

    public void setLocfee(int locfee) {
        this.locfee = locfee;
    }

    public int getIntopfee() {
        return intopfee;
    }

    public void setIntopfee(int intopfee) {
        this.intopfee = intopfee;
    }

    public int getOthfee() {
        return othfee;
    }

    public void setOthfee(int othfee) {
        this.othfee = othfee;
    }

    public String getFeename() {
        return feename;
    }

    public void setFeename(String feename) {
        this.feename = feename;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }
}
