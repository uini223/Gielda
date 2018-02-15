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

    private double aktualnaWartosc;

    private String nazwa;

    private HashSet<PosiadajacyPieniadze> setInwestorow;

    public Inwestycja(String nazwa, double aktualnaWartosc) {
        this.nazwa = nazwa;
        this.aktualnaWartosc = aktualnaWartosc;
        listaWartosciWCzasie = new HashMap<>();
        setInwestorow = new HashSet<>();
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
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh");
        synchronized (Main.getMonitor()){
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

    public void wyprzedajWszystko(){
        for (PosiadajacyPieniadze pp :
                getSetInwestorow()) {
            int ilosc = (int) pp.getHashMapInwestycji().get(this);
            double kapital = pp.getKapital() + ilosc*getAktualnaWartosc();
            pp.setKapital(kapital);
            pp.getHashMapInwestycji().remove(this);
        }
    }
}
