package gield;

public class Adres {
    private String miasto,nazwaUlicy;
    private int numerDomu;


    public Adres() {
        this.miasto = "miasto";
        this.nazwaUlicy = "nazwaUlicy";
        this.numerDomu = 3;
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
