package gield;

import controllers.Listable;
import gieldaPapierowWartosciowych.Indeks;
import main.Main;
import posiadajacyPieniadze.PosiadajacyPieniadze;
import rynekwalut.Waluta;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * klasa abstarkcyjna dla rynkow
 */
public abstract class Rynek implements Listable, Serializable{
    private String nazwa, kraj;
    private Waluta waluta;
    private Adres adres;
    private Indeks indeks;
    private double marzaOdTransakcji;

    /**
     * kontruktor rynku, losuje nazwe dla rynku, kraj, adres, oraz marze od transakcji
     */
    public Rynek() {
        String[] nazwyGield = {"GPW","NYM","WSP","PPP","APS","STH","UNE","OMA","PPA","ASA","PLN"};
        for (int i=0;i<nazwyGield.length;i++) {
            nazwyGield[i] += Integer.toString((int)(Math.random()*1000));
        }
        nazwa = nazwyGield[(int)(Math.random()*100)%nazwyGield.length];
        this.kraj = "kraj";

        this.adres = new Adres();
        this.marzaOdTransakcji = Math.random()*10;
    }

    /**
     * @param pp
     * implementowana "niżej"
     */
    public abstract void kupno(PosiadajacyPieniadze pp);

    /**
     * @param inwestycja
     * @param pp
     * implementowana "niżej"
     */
    public abstract void sprzedaz(Inwestycja inwestycja, PosiadajacyPieniadze pp);

    @Override
    public String toString() {
        return nazwa;
    }

    public String getNazwa() {
        return nazwa;
    }

    public String getKraj() {
        return kraj;
    }

    public Waluta getWaluta() {
        return waluta;
    }

    public void setWaluta(Waluta waluta) {
        this.waluta = waluta;
    }

    public Adres getAdres() {
        return adres;
    }

    public Indeks getIndeks() {
        return indeks;
    }

    public void setIndeks(Indeks indeks) {
        this.indeks = indeks;
    }

    public double getMarzaOdTransakcji() {
        return marzaOdTransakcji;
    }

    /**
     * @param kwota
     * @return
     * pobiera marze od transakcji zwraca zwiekszona kwote o marze
     */
    public double pobierzMarze(double kwota) {
        double marza = getMarzaOdTransakcji()/100;
        return kwota+(kwota*marza);
    }

    /**
     * @param kwota kwota zakupu (po pobraniu marzy)
     * @param ilosc ilosc zakupowanej inwestycji
     * @param pp kupujacy inwestor
     * @param inwestycja kupowana inwestycja
     */
    public void kupno(double kwota,int ilosc,PosiadajacyPieniadze pp, Inwestycja inwestycja){
        int pom;
        if(kwota<= pp.getKapital() && ilosc>0){
            if(pp.getHashMapInwestycji().containsKey(inwestycja)){
                pom = pp.getHashMapInwestycji().get(inwestycja).intValue();
                pp.getHashMapInwestycji().put(inwestycja,pom+ilosc);
            }
            else {
                pp.getHashMapInwestycji().put(inwestycja,ilosc);
            }
            pp.setKapital(pp.getKapital()-kwota);
            inwestycja.getSetInwestorow().add(pp);
            inwestycja.dodajKupujacego();
        }
    }

    /**
     * @param kwota kwota sprzedazy
     * @param ileSprzedanych ilosc sprzedanych jenostek inwestycji
     * @param pp inwestor sprzedajacy
     * @param inwestycja sprzedawana inwestycja
     */
    public void sprzedaz(double kwota,int ileSprzedanych,PosiadajacyPieniadze pp, Inwestycja inwestycja){
        int ilosc = (int) pp.getHashMapInwestycji().get(inwestycja);
        if(ileSprzedanych==ilosc){
            pp.getHashMapInwestycji().remove(inwestycja);
            inwestycja.getSetInwestorow().remove(pp);
        }
        else{
            pp.getHashMapInwestycji().put(inwestycja,ilosc-ileSprzedanych);
        }
        pp.setKapital(pp.getKapital()+kwota);
        inwestycja.dodajSprzedajacego();
    }
}

