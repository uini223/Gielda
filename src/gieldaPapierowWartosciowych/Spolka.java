package gieldaPapierowWartosciowych;

import controllers.Listable;
import gield.Inwestycja;
import gield.Rynek;
import main.Main;
import posiadajacyPieniadze.Inwestor;
import posiadajacyPieniadze.PosiadajacyPieniadze;

import java.io.Serializable;
import java.util.*;

/**
 * klasa dla spolki
 */
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

    private Boolean running;

    /**
     * @param rynek rynek na ktorym jest notowana spolka
     */
    public Spolka(Rynek rynek) {
        //super(nazwa);
        hashSetIndeksow = new HashSet<>();
        name = losowaNazwa();
        synchronized (Main.getMonitor()){
            dataPierwszejWyceny = new Date(Main.getContainer().getDate().getTime());
        }
        kapitalZakladowy = Math.random()*1000000;
        kapitalWlasny = kapitalZakladowy+Math.random()*1000000;
        liczbaAkcji = (int)(Math.random()*1000000)%100001+50000;
        kursOtwarcia = kapitalWlasny/liczbaAkcji;
        aktualnyKurs = kursOtwarcia;
        setMinimalnyKurs(kursOtwarcia);
        setMaksymalnyKurs(kursOtwarcia);
        przychod = (Math.random()*1000);
        zysk = przychod-(Math.random()*500);
        wolumen = 0;
        obroty = 0;
        akcjaSpolki = new Akcje(name,aktualnyKurs);
        akcjaSpolki.setRynek(rynek);
        akcjaSpolki.setSpolka(this);
        running = true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     *  "życie" spolki, generowanie zysku/ zmiana liczby akcji
     */
    public void run(){
        while(running){
            double a = Math.random()*100;
            if(a<40) {
                synchronized (Main.getMonitor()) {
                    generujZysk();
                    zmienLiczbeAkcji();
                }
            }
            try {
                Thread.sleep(1000*10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }

    /**
     * @return zwraca losowa nazwe spolki
     */
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

    /**
     * generuje przychod
     */
    public void generujPrzychod(){
        przychod+= Math.random()*10000;
    }

    /**
     * generuje zysk
     */
    public void generujZysk(){
        generujPrzychod();
        zysk = przychod - Math.random()*1000;
    }

    /**
     * losowo zmienia liczbe akcji o losowa wartosc
     */
    public void zmienLiczbeAkcji() {
        int znak = (int) (Math.random() * 100) % 2;
        if(liczbaAkcji>0){
            if (znak == 0) liczbaAkcji += (int)(Math.random() *liczbaAkcji);
            else{
                liczbaAkcji -= (int)(Math.random() * liczbaAkcji);
            }
        }
        if(liczbaAkcji==0){
            liczbaAkcji+=(int)(Math.random()*1000);
        }
    }

    /**
     * @param ilosc ilosc sprzedawanych przez spolke akcji
     * @param kwota kwota sprzedazy
     */
    public void sprzedajAkcje(int ilosc,double kwota){
        //zmianaKursu();
        akcjaSpolki.dodajKupujacego();
        liczbaAkcji-=ilosc;
        wolumen+=ilosc;
        obroty+=kwota;
    }

    /**
     * @param ilosc ilosc kupowanych przez spolke akcji
     * @param kwota
     */
    public void kupAkcje(int ilosc,double kwota){
        //zmianaKursu();
        akcjaSpolki.dodajSprzedajacego();
        liczbaAkcji+=ilosc;
        wolumen+=ilosc;
        obroty+=kwota;
    }

    public double getAktualnyKurs() {
        return aktualnyKurs;
    }

    public double getKapitalWlasny() {
        return kapitalWlasny;
    }

    public double getKapitalZakladowy() {
        return kapitalZakladowy;
    }

    public void setMaksymalnyKurs(double maksymalnyKurs) {
        this.maksymalnyKurs = maksymalnyKurs;
    }

    public void setMinimalnyKurs(double minimalnyKurs) {
        this.minimalnyKurs = minimalnyKurs;
    }

    public HashSet<Indeks> getHashSetIndeksow() {
        return hashSetIndeksow;
    }

    public Date getDataPierwszejWyceny() {
        return dataPierwszejWyceny;
    }

    public void setRunning(Boolean running) {
        this.running = running;
    }

    public int getWolumen() {
        return wolumen;
    }

    public double getZysk() {
        return zysk;
    }

    public double getObroty() {
        return obroty;
    }

    public double getPrzychod() {
        return przychod;
    }
}

