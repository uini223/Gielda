package gieldaPapierowWartosciowych;

import gield.Inwestycja;
import gield.Rynek;

import java.io.Serializable;
import java.util.*;

public class Spolka implements Runnable, Serializable{
    private String name;
    private Date dataPierwszejWyceny;
    private double kursOtwarcia, przychod, kapitalWlasny, kapitalZakladowy,  obroty, aktualnyKurs,
            maksymalnyKurs, minimalnyKurs,zysk;

    public int getLiczbaAkcji() {
        return liczbaAkcji;
    }

    public void setLiczbaAkcji(int liczbaAkcji) {
        this.liczbaAkcji = liczbaAkcji;
    }

    private int liczbaAkcji,wolumen;

    private Akcje akcjaSpolki;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Spolka(Rynek rynek) {
        //super(nazwa);
        name = losowaNazwa();
        dataPierwszejWyceny = new Date();
        kapitalZakladowy = Math.random()*1000000;
        kapitalWlasny = kapitalZakladowy+Math.random()*1000000;
        liczbaAkcji = (int)(Math.random()*1000000);
        kursOtwarcia = kapitalWlasny/liczbaAkcji;
        aktualnyKurs = kursOtwarcia;
        minimalnyKurs = kursOtwarcia;
        maksymalnyKurs = kursOtwarcia;
        przychod = 0;
        zysk = 0;
        wolumen = 0;
        obroty = 0;
        akcjaSpolki = new Akcje(rynek,name,aktualnyKurs);
        akcjaSpolki.setRynek(rynek);
    }

    private String losowaNazwa() {
        String[] pierwszaCzesc = {"New", "Star", "Bez", "Odrey", "Dog", "Skoki", "music"};
        String[] drugaCzesc = {"Corp", "z o.o","sp.j", "company", "fundation", "studio", "Ehdb"};
        int zakresPierwszy = pierwszaCzesc.length;
        int zakresDrugi = drugaCzesc.length;
        return pierwszaCzesc[(int)(Math.random()*100)%zakresPierwszy]
                +drugaCzesc[(int)(Math.random()*100)%zakresDrugi]+Integer.toString(
                (int)(Math.random()*100));
    }

    @Override
    public String toString() {
        return name;
    }

    public Akcje getAkcjaSpolki(){
        return akcjaSpolki;
    }

    public void generujPrzychod(){
        przychod+= Math.random()*10000;
    }

    public void generujZysk(){
        generujPrzychod();
        zysk = przychod - Math.random()*1000;
    }
    public void zmienLiczbeAkcji() {
        int znak = (int) (Math.random() * 100) % 2;
        if (znak == 0) liczbaAkcji += Math.random() * 100;
        else liczbaAkcji -= Math.random() * 100;
    }

    public void setAktualnyKurs(double aktualnyKurs) {
        if(aktualnyKurs<minimalnyKurs) minimalnyKurs = aktualnyKurs;
        if(aktualnyKurs>maksymalnyKurs) maksymalnyKurs = aktualnyKurs;
        this.aktualnyKurs = aktualnyKurs;
        akcjaSpolki.addWartoscAkcji(aktualnyKurs);
    }

    public void sprzedajAkcje(int ilosc){
        zmianaKursu();
        liczbaAkcji-=ilosc;
    }
    public void kupAkcje(int ilosc){
        zmianaKursu();
        liczbaAkcji+=ilosc;
    }
    private void zmianaKursu(){
        int rnd = (int)(Math.random()*100)%10+1;
        double wartosc = (aktualnyKurs*((double)rnd/100));
        int plus = (int)(Math.random()*100);
        if(plus>=50){
            setAktualnyKurs(aktualnyKurs+wartosc);
        }
        else {
            setAktualnyKurs(aktualnyKurs-wartosc);
        }
        getAkcjaSpolki().addWartoscAkcji(aktualnyKurs);
    }
    public double getAktualnyKurs() {
        return aktualnyKurs;
    }

    public void run(){
        while(true){
            generujZysk();
            zmienLiczbeAkcji();



        }
    }
}
