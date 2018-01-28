package rynekwalut;

import gield.Inwestycja;

import java.util.List;

public class Waluta extends Inwestycja {
    private List<String> listaKrajow;
    private double cenaKupna;
    private double cenaSprzedazy;
    private double przelicznik;
    private String nazwa;

    /**
     * @param cenaWPLN na ktora ma zostac przeliczona obecna waluta
     * @return zwraca wartosc w nowej walucie
     */
    public double przeliczCene(double cenaWPLN){
        return cenaWPLN*przelicznik;
    }

    public Waluta(String nazwa) {
        super(nazwa,0);
        this.nazwa = nazwa;
        if(nazwa.equals("PLN")){
            przelicznik = 1;
        }
        else{
            przelicznik = Math.random()*5;
        }
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
