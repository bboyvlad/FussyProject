package jd.persistence.dto;

/**
 * Created by eduardom on 10/17/16.
 */
public class rptCoordinatesDTO {

    private int fila;
    private String a;
    private String b;
    private String c;
    private String d;
    private String e;

    public rptCoordinatesDTO() {
    }

    public String getA() {
        return a;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public String getE() {
        return e;
    }

    public void setE(String e) {
        this.e = e;
    }
}
