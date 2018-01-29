package main;

import gield.Rynek;
import gieldaPapierowWartosciowych.Indeks;
import gieldaPapierowWartosciowych.Spolka;
import javafx.stage.Stage;
import posiadajacyPieniadze.PosiadajacyPieniadze;
import rynekwalut.RynekWalut;
import rynekwalut.Waluta;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public  class Container implements Serializable {
    private  volatile HashMap<String, Stage> stageHashMap ;

    private volatile HashMap<String, Rynek> hashMapRynkow;

    private volatile HashMap<String, PosiadajacyPieniadze> hashMapInwestorow;

    private volatile HashMap<String, Waluta> hashMapWalut;

    private volatile HashMap<String, Spolka> hashMapSpolek;

    private volatile HashMap<String, Indeks> hashMapIndeksow;

    private volatile RynekWalut rynekWalut;
    private volatile Date date;

    public  Container() {
        String[] waluty = {"PLN","GBP","USD","KYS","BTC"};
        stageHashMap = new HashMap<>();
        hashMapRynkow = new HashMap<>();
        hashMapInwestorow = new HashMap<>();
        hashMapWalut = new HashMap<>();
        hashMapIndeksow = new HashMap<>();
        hashMapSpolek = new HashMap<>();
        rynekWalut = new RynekWalut();
        date = new Date();
        for (String a: waluty
                ) {
            hashMapWalut.put(a,new Waluta(a,rynekWalut));
        }
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Indeks getIndeks(String s){
        return hashMapIndeksow.get(s);
    }

    public void addSpolka(Spolka spolka){
        hashMapSpolek.put(spolka.getName(),spolka);
    }

    public HashMap<String, Spolka> getHashMapSpolek() {
        return hashMapSpolek;
    }

    public HashMap<String, Indeks> getHashMapIndeksow() {
        return hashMapIndeksow;
    }

    public void addIndeks(Indeks indeks){
        hashMapIndeksow.put(indeks.getNazwa(),indeks);
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

    public PosiadajacyPieniadze getPP(String name){
        return hashMapInwestorow.get(name);
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

    public void addStage(String name, Stage stage){
        stageHashMap.put(name,stage);
    }

    public Stage getStage(String name){
        return stageHashMap.get(name);
    }

    public HashMap<String,Stage> getStageHashMap(){
        return stageHashMap;
    }

}
