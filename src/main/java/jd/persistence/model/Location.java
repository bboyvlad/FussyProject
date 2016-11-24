package jd.persistence.model;

import javax.persistence.*;

/**
 * Created by eduardom on 10/4/16.
 */
@Entity
@Table(name = "Location")
public class Location {

    private long id;
    private String name;
    private String city;
    private String country;
    private String IATA;
    private String ICAO;
    private float latitude;
    private float longitude;
    private int elevation;
    private int timezone;
    private char dst;
    private String region;
    private boolean available;

    public Location() {
    }

    public Location(long id, String name, String city, String country, String IATA, String ICAO, float latitude, float longitude, int elevation, int timezone, char dst, String region) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.country = country;
        this.IATA = IATA;
        this.ICAO = ICAO;
        this.latitude = latitude;
        this.longitude = longitude;
        this.elevation = elevation;
        this.timezone = timezone;
        this.dst = dst;
        this.region = region;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LOCATION_ID", unique = true, nullable = false)
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getIATA() {
        return IATA;
    }

    public void setIATA(String IATA) {
        this.IATA = IATA;
    }

    public String getICAO() {
        return ICAO;
    }

    public void setICAO(String ICAO) {
        this.ICAO = ICAO;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public int getElevation() {
        return elevation;
    }

    public void setElevation(int elevation) {
        this.elevation = elevation;
    }

    public int getTimezone() {
        return timezone;
    }

    public void setTimezone(int timezone) {
        this.timezone = timezone;
    }

    public char getDst() {
        return dst;
    }

    public void setDst(char dst) {
        this.dst = dst;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
