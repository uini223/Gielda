package rynekwalut;

import gield.Inwestycja;
import gield.Rynek;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Waluta extends Inwestycja {
    private List<String> listaKrajow;
    private double cenaKupna;
    private double cenaSprzedazy;
    private double przelicznik;
    private String nazwa;

    /**
     * @param cenaWPLN na ktora ma zostac przeliczona na obecną waluta
     * @return zwraca wartosc w nowej walucie
     */
    public double przeliczCene(double cenaWPLN){
        return cenaWPLN/getAktualnaWartosc();
    }

    public double przelicCeneNaPLN(double cena){return cena*getAktualnaWartosc();}

    public double getPrzelicznik() {
        return przelicznik;
    }

    public void setPrzelicznik(double przelicznik) {
        this.przelicznik = przelicznik;
    }

    public Waluta(String nazwa) {
        super( nazwa,0);
        listaKrajow = new ArrayList<>();
        this.nazwa = nazwa;
        if(nazwa.equals("PLN")){
            przelicznik = 1;
        }
        else{
            przelicznik = Math.random()*5;
        }
        setPoczatkowaWartosc(przelicznik);
        setAktualnaWartosc(przelicznik);
        setNajmniejszaWartosc(przelicznik);
        setNajwiekszaWartosc(przelicznik);
        listaKrajow.add("Polska");
        listaKrajow.add("Węgry");
    }

    @Override
    public String toString() {
        return nazwa;
    }

    public void dodajPanstwo(String text) {
        listaKrajow.add(text);
    }

    public List getListaKrajow() {
        return listaKrajow;
    }
}
