package gieldaPapierowWartosciowych;

import controllers.Listable;
import gield.Inwestycja;
import gield.Rynek;
import main.Main;

import java.io.Serializable;
import java.util.*;

public class Spolka implements Runnable, Serializable, Listable
{
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

    private Indeks indeksSpolki;

    private HashSet<Indeks> hashSetIndeksow;

    public Spolka(Rynek rynek) {
        //super(nazwa);
        hashSetIndeksow = new HashSet<>();
        name = losowaNazwa();
        synchronized (Main.getMonitor()){
            dataPierwszejWyceny = new Date(Main.getContainer().getDate().getTime());
        }
        kapitalZakladowy = Math.random()*1000000;
        kapitalWlasny = kapitalZakladowy+Math.random()*1000000;
        liczbaAkcji = (int)(Math.random()*1000000);
        kursOtwarcia = kapitalWlasny/liczbaAkcji;
        aktualnyKurs = kursOtwarcia;
        setMinimalnyKurs(kursOtwarcia);
        setMaksymalnyKurs(kursOtwarcia);
        przychod = 0;
        zysk = 0;
        wolumen = 0;
        obroty = 0;
        akcjaSpolki = new Akcje(name,aktualnyKurs);
        akcjaSpolki.setRynek(rynek);
        akcjaSpolki.setSpolka(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void run(){
        while(true){
            generujZysk();
            zmienLiczbeAkcji();



        }
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
    public void sprzedajAkcje(int ilosc,double kwota){
        //zmianaKursu();
        akcjaSpolki.dodajKupujacego();
        liczbaAkcji-=ilosc;
        wolumen+=ilosc;
        obroty+=kwota;
    }
    public void kupAkcje(int ilosc,double kwota){
        //zmianaKursu();
        akcjaSpolki.dodajSprzedajacego();
        liczbaAkcji+=ilosc;
        wolumen+=ilosc;
        obroty+=kwota;
    }
    private void zmianaKursu(){
        /*
        int rnd = (int)(Math.random()*100)%25+1;
        double wartosc = (aktualnyKurs*((double)rnd/100));
        int plus = (int)(Math.random()*100);
        if(plus%10<6){
            setAktualnyKurs(aktualnyKurs+wartosc);
        }
        else {
            setAktualnyKurs(aktualnyKurs-wartosc);
        }
        if(aktualnyKurs<getMinimalnyKurs()){
            setMinimalnyKurs(aktualnyKurs);
            akcjaSpolki.setNajmniejszaWartosc(aktualnyKurs);
        }
        if(aktualnyKurs>getMaksymalnyKurs()){
            setMaksymalnyKurs(aktualnyKurs);
            akcjaSpolki.setNajwiekszaWartosc(aktualnyKurs);
        }
        getAkcjaSpolki().addWartoscAkcji(aktualnyKurs);*/

    }

    public double getAktualnyKurs() {
        return aktualnyKurs;
    }

    public double getKapitalWlasny() {
        return kapitalWlasny;
    }

    public void setKapitalWlasny(double kapitalWlasny) {
        this.kapitalWlasny = kapitalWlasny;
    }

    public double getKapitalZakladowy() {
        return kapitalZakladowy;
    }

    public void setKapitalZakladowy(double kapitalZakladowy) {
        this.kapitalZakladowy = kapitalZakladowy;
    }

    public double getMaksymalnyKurs() {
        return maksymalnyKurs;
    }

    public void setMaksymalnyKurs(double maksymalnyKurs) {
        this.maksymalnyKurs = maksymalnyKurs;
    }

    public double getMinimalnyKurs() {
        return minimalnyKurs;
    }

    public void setMinimalnyKurs(double minimalnyKurs) {
        this.minimalnyKurs = minimalnyKurs;
    }

    public Indeks getIndeksSpolki() {
        return indeksSpolki;
    }

    public void setIndeksSpolki(Indeks indeksSpolki) {
        this.indeksSpolki = indeksSpolki;
    }

    public HashSet<Indeks> getHashSetIndeksow() {
        return hashSetIndeksow;
    }

    public void setHashSetIndeksow(HashSet<Indeks> hashSetIndeksow) {
        this.hashSetIndeksow = hashSetIndeksow;
    }

    public Date getDataPierwszejWyceny() {
        return dataPierwszejWyceny;
    }

    public void setDataPierwszejWyceny(Date dataPierwszejWyceny) {
        this.dataPierwszejWyceny = dataPierwszejWyceny;
    }
}

