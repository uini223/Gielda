package gieldaPapierowWartosciowych;

import controllers.Listable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Indeks implements Listable {

    private GieldaPapierowWartosciowych rodzic;

    private Map<String,Spolka> hashMapSpolek;

    private String name;

    public Map<String, Spolka> getHashMapSpolek() {
        return hashMapSpolek;
    }

    public void setHashMapSpolek(Map<String, Spolka> hashMapSpolek) {
        this.hashMapSpolek = hashMapSpolek;
    }

    public Indeks(GieldaPapierowWartosciowych rodzic) {
        this.rodzic = rodzic;
        hashMapSpolek = new ConcurrentHashMap<>();
        name = "indeks" + Integer.toString((int)((Math.random())*10000));
        Spolka spolka;
        for (int i = 0; i < 3; i++) {
            spolka = new Spolka();
            hashMapSpolek.put(spolka.getName(),spolka);
        }
        rodzic.addIndeks(this);
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
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

    public void setName(String name) {
        this.name = name;
    }

    private int parametrDoboruSpolek;

    public void dodajSpolke(Spolka a){
        hashMapSpolek.put(a.getName(),a);
        rodzic.aktualizujSpolki();
    }
    public void usunSpolke(Spolka a){

    }

}
