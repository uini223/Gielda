package gield;

import controllers.Listable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public abstract class Inwestycja implements Listable{
    private double aktualnaWartosc;

    private String nazwa;

    public double getAktualnaWartosc() {
        return aktualnaWartosc;
    }

    public String getNazwa() {
        return nazwa;
    }

    private Map<String,Number> listaWartosciWCzasie;

    public Inwestycja(String nazwa,double aktualnaWartosc) {
        this.nazwa = nazwa;
        this.aktualnaWartosc = aktualnaWartosc;
        listaWartosciWCzasie = new HashMap<>();
    }
    public void addWartoscAkcji(double wartoscAkcji){
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy mm:ss");
        aktualnaWartosc = wartoscAkcji;
        Date data = new Date();
        //data.setTime(data.getTime()+1000*86400);
        listaWartosciWCzasie.put(df.format(data),wartoscAkcji);
    }

    public Map<String,Number> getWartosciAkcji(){
        return listaWartosciWCzasie;
    }
}
