package gield;

import controllers.Listable;
import main.Main;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public abstract class Inwestycja implements Listable, Serializable{
    private Rynek rynek;
    private double aktualnaWartosc;

    private String nazwa;

    public double getAktualnaWartosc() {
        return aktualnaWartosc;
    }

    public String getNazwa() {
        return nazwa;
    }

    private Map<String,Number> listaWartosciWCzasie;

    public Inwestycja(String nazwa, double aktualnaWartosc) {
        this.nazwa = nazwa;
        this.aktualnaWartosc = aktualnaWartosc;
        listaWartosciWCzasie = new HashMap<>();
    }

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
}
