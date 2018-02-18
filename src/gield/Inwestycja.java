package gield;

import controllers.Listable;
import main.Main;
import posiadajacyPieniadze.Inwestor;
import posiadajacyPieniadze.PosiadajacyPieniadze;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public abstract class Inwestycja implements Listable, Serializable{
    private Rynek rynek;

    private double aktualnaWartosc,najmniejszaWartosc,najwiekszaWartosc,poczatkowaWartosc;

    private int kupujacy,sprzedajacy,licznik;

    private String nazwa;

    private HashSet<PosiadajacyPieniadze> setInwestorow;

    public Inwestycja(String nazwa, double aktualnaWartosc) {
        this.nazwa = nazwa;
        this.aktualnaWartosc = aktualnaWartosc;
        najmniejszaWartosc = aktualnaWartosc;
        najwiekszaWartosc = aktualnaWartosc;
        listaWartosciWCzasie = new HashMap<>();
        setInwestorow = new HashSet<>();
        resetujSprzedajacychKupujacych();
        licznik =0;
    }

    public double getAktualnaWartosc() {
        return aktualnaWartosc;
    }

    public String getNazwa() {
        return nazwa;
    }

    private Map<String,Number> listaWartosciWCzasie;

    public Rynek getRynek() {
        return rynek;
    }

    public void addWartoscAkcji(double wartoscAkcji){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        synchronized (Main.getMonitor()){
            if(wartoscAkcji<najmniejszaWartosc){
                najmniejszaWartosc = wartoscAkcji;
            }
            if(wartoscAkcji>najwiekszaWartosc){
                najwiekszaWartosc = wartoscAkcji;
            }
            aktualnaWartosc = wartoscAkcji;
            listaWartosciWCzasie.put(df.format(Main.getContainer().getDate()),wartoscAkcji);
        }
    }

    public void setRynek(Rynek rynek) {
        this.rynek = rynek;
    }

    public Map<String,Number> getWartosciAkcji(){
        return listaWartosciWCzasie;
    }

    public HashSet<PosiadajacyPieniadze> getSetInwestorow() {
        return setInwestorow;
    }

    public void wyprzedajWszystko(double cena){
        for (PosiadajacyPieniadze pp :
                getSetInwestorow()) {
            int ilosc = (int) pp.getHashMapInwestycji().get(this);
            double kapital = pp.getKapital() + ilosc * cena;
            pp.setKapital(kapital);
            pp.getHashMapInwestycji().remove(this);
        }
    }

    public double getNajwiekszaWartosc() {
        return najwiekszaWartosc;
    }

    public void setNajwiekszaWartosc(double najwiekszaWartosc) {
        this.najwiekszaWartosc = najwiekszaWartosc;
    }

    public double getNajmniejszaWartosc() {
        return najmniejszaWartosc;
    }

    public void setNajmniejszaWartosc(double najmniejszaWartosc) {
        this.najmniejszaWartosc = najmniejszaWartosc;
    }

    public void setAktualnaWartosc(double aktualnaWartosc) {
        this.aktualnaWartosc = aktualnaWartosc;
    }

    public double getPoczatkowaWartosc() {
        return poczatkowaWartosc;
    }

    public void setPoczatkowaWartosc(double poczatkowaWartosc) {
        this.poczatkowaWartosc = poczatkowaWartosc;
    }

    public void dodajKupujacego(){
        kupujacy+=1;
    }

    public void dodajSprzedajacego(){
        sprzedajacy+=1;
    }

    public void resetujSprzedajacychKupujacych(){
        sprzedajacy = 0;
        kupujacy =0;
    }

    public void obliczNowyKurs(){
        if(rynek!=null) {
            double kurs;
            synchronized (Main.getMonitor()) {
                double old = aktualnaWartosc;
                int rnd = (int) (Math.random() * 100) % 10 + 1;
                double wartosc = (old * ((double) rnd / 100));
                int plus = (int) (Math.random() * 100);
                if(kupujacy+sprzedajacy==0 && licznik<5){
                    kurs = old;
                    licznik++;
                }
                else {
                    if (plus % 2 == 0) {
                        kurs = old + wartosc;
                    } else {
                        kurs = old - wartosc;
                    }
                    licznik=0;
                }


            }
            addWartoscAkcji(kurs);
        }
    }

}
