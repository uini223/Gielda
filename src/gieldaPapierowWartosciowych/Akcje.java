package gieldaPapierowWartosciowych;

import gield.Inwestycja;
import javafx.scene.chart.NumberAxis;

import java.text.SimpleDateFormat;
import java.util.*;

public class Akcje extends Inwestycja{
    private double aktualnaWartosc;

    private Map<Date,Number> listaWartosciWCzasie;

    private String nazwaSpolki;

    public Akcje(String nazwaSpolki,double aktualnaWartosc) {
        this.aktualnaWartosc = aktualnaWartosc;
        this.nazwaSpolki = nazwaSpolki;
        listaWartosciWCzasie = new HashMap<>();
    }

    public void addWartoscAkcji(double wartoscAkcji){
        aktualnaWartosc = wartoscAkcji;
        listaWartosciWCzasie.put(new Date(),wartoscAkcji);
    }

    public Map<Date,Number> getWartosciAkcji(){
        return listaWartosciWCzasie;
    }

    public void generujNowaWartosc(){
        listaWartosciWCzasie.put(new Date(),Math.random());
    }

    public String getNazwaSpolki() {
        return nazwaSpolki;
    }

    @Override
    public String toString() {
        return nazwaSpolki;
    }
}
