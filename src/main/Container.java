package main;

import daySimulation.DaySimulation;
import gield.Rynek;
import gieldaPapierowWartosciowych.Indeks;
import gieldaPapierowWartosciowych.Spolka;
import posiadajacyPieniadze.FunduszInwestycyjny;
import posiadajacyPieniadze.PosiadajacyPieniadze;
import rynekSurowcow.Surowiec;
import rynekwalut.Waluta;

import java.io.Serializable;
import java.util.*;

/**
 * klasa kontenera przechowuje stan swiata, zawiera wszyskie potrzebne elementy
 */
public  class Container implements Serializable {
    private volatile HashMap<String, Rynek> hashMapRynkow;

    private volatile HashMap<String, PosiadajacyPieniadze> hashMapInwestorow;

    private volatile HashMap<String, Waluta> hashMapWalut;

    private volatile HashSet<Waluta> walutaSet;

    private volatile HashMap<String, Spolka> hashMapSpolek;

    private volatile HashMap<String, Indeks> hashMapIndeksow;

    private volatile HashMap<String, Surowiec> hashMapSurowcow;

    private volatile HashMap<String, FunduszInwestycyjny> hashMapFunduszy;

    private volatile HashSet<Surowiec> surowiecSet;

    private volatile Date date;

    private volatile transient DaySimulation daySimulation;

    public  Container() {
        hashMapFunduszy = new HashMap<>();
        surowiecSet = new HashSet<>();
        hashMapSurowcow = new HashMap<>();
        walutaSet = new HashSet<>();
        hashMapRynkow = new HashMap<>();
        hashMapInwestorow = new HashMap<>();
        hashMapWalut = new HashMap<>();
        hashMapIndeksow = new HashMap<>();
        hashMapSpolek = new HashMap<>();
        date = new Date();
        hashMapWalut.put("PLN",new Waluta("PLN"));
        for(int i=0;i<10;i++){
            addNewWaluta();
        }
    }

    public Date getDate() {
        return date;
    }

    public Indeks getIndeks(String s){
        return hashMapIndeksow.get(s);
    }

    public HashMap<String, Spolka> getHashMapSpolek() {
        return hashMapSpolek;
    }

    public HashMap<String, Indeks> getHashMapIndeksow() {
        return hashMapIndeksow;
    }

    public HashMap<String, Waluta> getHashMapWalut() {
        return hashMapWalut;
    }

    public void addWaluta(String name, Waluta waluta){
        hashMapWalut.put(name,waluta);
    }

    public void addRynek(Rynek rynek){
        hashMapRynkow.put(rynek.getNazwa(),rynek);
    }

    public HashMap<String, Rynek> getHashMapRynkow(){
        return hashMapRynkow;
    }

    public HashMap<String, PosiadajacyPieniadze> getHashMapInwestorow(){
        return hashMapInwestorow;
    }

    public void addPosiadajacyPieniadze(PosiadajacyPieniadze p){
        hashMapInwestorow.put(p.getName(),p);
    }

    public Rynek getRynek(String name){
        return hashMapRynkow.get(name);
    }

    /**
     * @return zwaraca dodana walute do swiata
     */
    public Waluta addNewWaluta(){
        char characters[] = new char[26];
        for(int i=0; i<26;i++){
            characters[i]=(char)('A'+i);
        }
        String name="";
        for(int i=0;i<4;i++){
            name+=characters[(int)(Math.random()*1000)%26];
        }
        Waluta waluta = new Waluta(name);
        walutaSet.add(waluta);
        addWaluta(name,waluta);
        return waluta;
    }

    public HashSet<Waluta> getWalutaSet() {
        return walutaSet;
    }

    /**
     * @return zwraca dodany do swiata surowiec
     */
    public Surowiec addNewSurowiec(){
        Waluta waluta=null;
        int a = (int)(Math.random()*1000)%hashMapWalut.values().size();
        int n = 0;
        for (Waluta w :
                hashMapWalut.values()) {
            if (a == n) {
                waluta = w;
            }
            n++;
        }
        Surowiec surowiec = new Surowiec("Maliny"+String.valueOf(Math.random()*100),"Kg",waluta);
        surowiecSet.add(surowiec);
        hashMapSurowcow.put(surowiec.getName(),surowiec);
        return surowiec;
    }
    public HashMap<String, Surowiec> getHashMapSurowcow() {
        return hashMapSurowcow;
    }

    public HashSet<Surowiec> getSurowiecSet() {
        return surowiecSet;
    }

    public DaySimulation getDaySimulation() {
        return daySimulation;
    }

    public void setDaySimulation(DaySimulation daySimulation) {
        this.daySimulation = daySimulation;
    }

    public HashMap<String, FunduszInwestycyjny> getHashMapFunduszy() {
        return hashMapFunduszy;
    }

    public void addIndeks(Indeks indeks) {
        hashMapIndeksow.put(indeks.getName(),indeks);
    }

    public void addSpolka(Spolka spolka) {
        hashMapSpolek.put(spolka.getName(),spolka);
    }
}
