package jd.persistence.dto;

import java.util.Date;

/**
 * Created by eduardom on 11/22/16.
 */
public class newPriceDto {
    private long location;
    private long provider;
    private long aviation;
    private String pricename;
    private boolean prepaid;
    private Date validto;
    private double frompound;
    private double topound ;
    private Date fromdate;
    private Date todate;
    private int interval;
    private String currency;
    private String measure;
    private String unit;
    private String unitdesc ;
    private double price1 ;
    private double price2;
    private double price3;
    private double cost1 ;
    private double cost2 ;
    private double cost3 ;
    private int diff ;
    private int perc ;
    private int margin ;
    private int minimun ;
    private int maximun ;
    private boolean feesenable;
    private int locfee ;
    private int intopfee ;
    private int othfee ;
    private String feename ;

    public newPriceDto() {
    }

    public long getLocation() {
        return location;
    }

    public void setLocation(long location) {
        this.location = location;
    }

    public long getProvider() {
        return provider;
    }

    public void setProvider(long provider) {
        this.provider = provider;
    }

    public long getAviation() {
        return aviation;
    }

    public void setAviation(long aviation) {
        this.aviation = aviation;
    }

    public String getPricename() {
        return pricename;
    }

    public void setPricename(String pricename) {
        this.pricename = pricename;
    }

    public boolean isPrepaid() {
        return prepaid;
    }

    public void setPrepaid(boolean prepaid) {
        this.prepaid = prepaid;
    }

    public Date getValidto() {
        return validto;
    }

    public void setValidto(Date validto) {
        this.validto = validto;
    }

    public double getFrompound() {
        return frompound;
    }

    public void setFrompound(double frompound) {
        this.frompound = frompound;
    }

    public double getTopound() {
        return topound;
    }

    public void setTopound(double topound) {
        this.topound = topound;
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
}
