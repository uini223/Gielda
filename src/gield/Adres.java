package gield;

import java.io.Serializable;

/**
 * klasa reprezentujaca adres
 */
public class Adres implements Serializable{
    private String miasto,nazwaUlicy;
    private int numerDomu;


    public Adres(String miasto,String nazwaUlicy,int numerDomu) {
        this.miasto = miasto;
        this.nazwaUlicy = nazwaUlicy;
        this.numerDomu = numerDomu;
    }

    @Override
    public String toString() {
        return "Adres{" +
                "miasto='" + miasto + '\'' +
                 + '\'' +
                ", nazwaUlicy='" + nazwaUlicy + '\'' +
                ", numerDomu=" + numerDomu +
                '}';
    }

    public String getMiasto() {
        return miasto;
    }

    public void setMiasto(String miasto) {
        this.miasto = miasto;
    }

    public String getNazwaUlicy() {
        return nazwaUlicy;
    }

    public void setNazwaUlicy(String nazwaUlicy) {
        this.nazwaUlicy = nazwaUlicy;
    }

    public int getNumerDomu() {
        return numerDomu;
    }

    public void setNumerDomu(int numerDomu) {
        this.numerDomu = numerDomu;
    }

}
