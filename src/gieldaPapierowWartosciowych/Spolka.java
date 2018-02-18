package gieldaPapierowWartosciowych;

import controllers.Listable;
import gield.Inwestycja;
import gield.Rynek;
import main.Main;
import posiadajacyPieniadze.Inwestor;
import posiadajacyPieniadze.PosiadajacyPieniadze;

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

    private Boolean running;

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
        przychod = 0;
        zysk = 0;
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

    public void run(){
        while(running){
            double a = Math.random()*1000;
            if(a<50) {
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

    public void wyprzedajSpolke(double cena){
        for (PosiadajacyPieniadze inwestor :
                akcjaSpolki.getSetInwestorow()) {
            double kwota = (int)inwestor.getHashMapInwestycji().get(akcjaSpolki)*cena;
            inwestor.setKapital(inwestor.getKapital()+kwota);
            inwestor.getHashMapInwestycji().remove(akcjaSpolki);
        }
    }

    public Boolean getRunning() {
        return running;
    }

    public void setRunning(Boolean running) {
        this.running = running;
    }
}

