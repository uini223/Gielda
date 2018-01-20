package rynekwalut;

import gield.Inwestycja;
import main.Main;

import java.util.List;

public class Waluta extends Inwestycja {
    private List<String> listaKrajow;
    private double cenaKupna;
    private double cenaSprzedazy;
    private double przelicznik;
    private String nazwa;

    /**
     * @param a waluta na ktora ma zostac przeliczona obecna waluta
     * @return zwraca wartosc w nowej walucie
     */
    public double przeliczCene(Waluta a){
        return 0;
    }

    public Waluta(String nazwa) {
        this.nazwa = nazwa;

    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    @Override
    public String toString() {
        return "Waluta{" +
                "nazwa='" + nazwa + '\'' +
                '}';
    }
}
