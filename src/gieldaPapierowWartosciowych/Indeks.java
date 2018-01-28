package gieldaPapierowWartosciowych;

import controllers.Listable;
import gield.Inwestycja;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Indeks extends Inwestycja implements Listable {

    private GieldaPapierowWartosciowych rodzic;

    private Map<String,Spolka> hashMapSpolek;

    public Map<String, Spolka> getHashMapSpolek() {
        return hashMapSpolek;
    }

    public void setHashMapSpolek(Map<String, Spolka> hashMapSpolek) {
        this.hashMapSpolek = hashMapSpolek;
    }

    public Indeks(GieldaPapierowWartosciowych rodzic) {
        super("indeks" + Integer.toString((int)((Math.random())*10000)),0);
        //name = "indeks" + Integer.toString((int)((Math.random())*10000));
        this.rodzic = rodzic;
        hashMapSpolek = new ConcurrentHashMap<>();
        Spolka spolka;
        for (int i = 0; i < 3; i++) {
            spolka = new Spolka();
            hashMapSpolek.put(spolka.getName(),spolka);
        }
        rodzic.addIndeks(this);
    }

    @Override
    public String toString() {
        return getNazwa();
    }

    public GieldaPapierowWartosciowych getRodzic() {
        return rodzic;
    }

    public void setRodzic(GieldaPapierowWartosciowych rodzic) {
        this.rodzic = rodzic;
    }

    public int getParametrDoboruSpolek() {
        return parametrDoboruSpolek;
    }

    public void setParametrDoboruSpolek(int parametrDoboruSpolek) {
        this.parametrDoboruSpolek = parametrDoboruSpolek;
    }

    private int parametrDoboruSpolek;

    public void dodajSpolke(Spolka a){
        hashMapSpolek.put(a.getName(),a);
        rodzic.aktualizujSpolki();
    }
    public void aktualizujWartosc(){
        double wartosc = 0;
        for (String s :
                hashMapSpolek.keySet()) {
            wartosc+=hashMapSpolek.get(s).getAkcjaSpolki().getAktualnaWartosc();
        }
        this.addWartoscAkcji(wartosc);
    }
    public void usunSpolke(Spolka a){

    }

}
