package gield;

import controllers.Listable;
import main.Main;
import observers.Observer;
import posiadajacyPieniadze.PosiadajacyPieniadze;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * klasa abstrakcyjna Inwestycja dla podklas akcja,indeks,surowiec,waluta
 */
public abstract class Inwestycja implements Listable, Serializable{
    private Rynek rynek;

    private double aktualnaWartosc,najmniejszaWartosc,najwiekszaWartosc,poczatkowaWartosc;

    private int kupujacy,sprzedajacy,licznik;

    private String name;

    private HashSet<PosiadajacyPieniadze> setInwestorow;

    private List<Observer> observers;

    /**
     * @param name
     * @param aktualnaWartosc
     * konstruktor obiektu
     */
    public Inwestycja(String name, double aktualnaWartosc) {
        this.name = name;
        this.aktualnaWartosc = aktualnaWartosc;
        najmniejszaWartosc = aktualnaWartosc;
        najwiekszaWartosc = aktualnaWartosc;

        listaWartosciWCzasie = new HashMap<>();
        setInwestorow = new HashSet<>();
        resetujSprzedajacychKupujacych();
        licznik =0;
    }
    public Inwestycja(){
        listaWartosciWCzasie = new HashMap<>();
        observers = new ArrayList<>();
    }

    public double getAktualnaWartosc() {
        return aktualnaWartosc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    private Map<String,Number> listaWartosciWCzasie;

    public Rynek getRynek() {
        return rynek;
    }

    /**
     * @param wartoscAkcji
     * dodaje wartosc inwestycji(nie tylko akcji) do hashmapy wartosci w czasie
     */
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

    /**
     * @param cena
     * wyprzedanie wszystkiego, funkcja zwraca wszystkim inwestorom zainwestowane pieniadze
     */
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

    /**
     * dodaje jednego kupującego w dnaym "dniu"
     */
    public void dodajKupujacego(){
        kupujacy+=1;
    }

    /**
     * dodaje jednego sprzedajacego w danym "dniu"
     */
    public void dodajSprzedajacego(){
        sprzedajacy+=1;
    }

    /**
     * ustawia ilosc sprzedajacych i kupujacyh na 0
     */
    public void resetujSprzedajacychKupujacych(){
        sprzedajacy = 0;
        kupujacy =0;
    }

    public void notifyAllObservers(){
        for (Observer observer: observers){
            observer.update();
        }
    }

    public void attachObserver(Observer observer){
        observers.add(observer);
    }
    public void detachObserver(Observer observer){
        observers.remove(observer);
    }
    /**
     * wyznacza nowy kurs na podstawie kupujacych i sprzedajacych (glownie losowo)
     */
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
            notifyAllObservers();
            addWartoscAkcji(kurs);
        }
    }

}
