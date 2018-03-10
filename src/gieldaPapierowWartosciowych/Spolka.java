package gieldaPapierowWartosciowych;

import controllers.Listable;
import gield.Rynek;
import main.Main;

import java.io.Serializable;
import java.util.*;

/**
 * klasa dla spolki
 */
public class Spolka implements Runnable, Serializable, Listable
{
    private String name;
    private Date dataPierwszejWyceny;
    private double kursOtwarcia, przychod, kapitalWlasny, kapitalZakladowy, obroty, zysk;
    private int liczbaAkcji,wolumen;
    private Akcje akcjaSpolki;
    private HashMap<String,Indeks> hashMapIndeksow;
    private Boolean running;
    private Rynek rynek;


    /**
     * @param rynek rynek na ktorym jest notowana spolka
     */
    public Spolka(Rynek rynek) {

        akcjaSpolki = new Akcje();
        akcjaSpolki.setRynek(rynek);
        akcjaSpolki.setSpolka(this);

    }

    public Spolka(){
        hashMapIndeksow = new HashMap<>();
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


    @Override
    public String toString() {
        return name;
    }

    public Akcje getAkcjaSpolki(){
        return akcjaSpolki;
    }

    public void setObroty(double obroty) {
        this.obroty = obroty;
    }

    public void setAkcjaSpolki(Akcje akcjaSpolki) {
        this.akcjaSpolki = akcjaSpolki;
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


    public double getKapitalWlasny() {
        return kapitalWlasny;
    }

    public double getKapitalZakladowy() {
        return kapitalZakladowy;
    }

    public HashMap<String,Indeks> getHashMapIndeksow() {
        return hashMapIndeksow;
    }

    public Date getDataPierwszejWyceny() {
        return dataPierwszejWyceny;
    }

    public void setLiczbaAkcji(int liczbaAkcji) {
        this.liczbaAkcji = liczbaAkcji;
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

    public void setDataPierwszejWyceny(Date data) {
        dataPierwszejWyceny = data;
    }

    public void setKapitalZakladowy(double kapital) {
        this.kapitalZakladowy = kapital;
    }

    public void setKapitalWlasny(double kapitalWlasny) {
        this.kapitalWlasny = kapitalWlasny;
    }

    public void setKursOtwarcia(double kursOtwarcia) {
        this.kursOtwarcia = kursOtwarcia;
    }

    public double getKursOtwarcia() {
        return kursOtwarcia;
    }


    public void setPrzychod(double przychod) {
        this.przychod = przychod;
    }

    public void setZysk(double zysk) {
        this.zysk = zysk;
    }

    public void setWolumen(int wolumen) {
        this.wolumen = wolumen;
    }

    public void setObroty(int obroty) {
        this.obroty = obroty;
    }

    public int getLiczbaAkcji() {
        return liczbaAkcji;
    }

    public Rynek getRynek() {
        return rynek;
    }

    public void setRynek(Rynek rynek) {
        this.rynek = rynek;
    }

    public void addIndeks(Indeks indeks) {
        hashMapIndeksow.put(indeks.getName(),indeks);
    }
}

