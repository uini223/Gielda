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

public abstract class Rynek implements Listable, Serializable{
    private String nazwa, kraj;
    private Waluta waluta;
    private Adres adres;
    private Indeks indeks;
    private double marzaOdTransakcji;
    private double przelicznik;
    private HashMap<String,Inwestycja> hashMapInwestycji;

    public double getPrzelicznik() {
        return przelicznik;
    }

    public void setPrzelicznik(double przelicznik) {
        this.przelicznik = przelicznik;
    }

    public Rynek() {
        String[] nazwyGield = {"GPW","NYM","WSP","PPP","APS","STH","UNE","OMA","PPA","ASA","PLN"};
        for (int i=0;i<nazwyGield.length;i++) {
            nazwyGield[i] += Integer.toString((int)(Math.random()*1000));
        }

        hashMapInwestycji = new HashMap<>();
        nazwa = nazwyGield[(int)(Math.random()*100)%nazwyGield.length];
        this.kraj = "kraj";

        this.adres = new Adres();
        this.marzaOdTransakcji = 0.5;
    }

    public abstract void kupno(PosiadajacyPieniadze pp);

    public abstract void sprzedaz(Inwestycja inwestycja, PosiadajacyPieniadze pp);

    @Override
    public String toString() {
        return nazwa;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        synchronized(Main.getContainer()){
            Main.getContainer().getHashMapRynkow().remove(this.nazwa);
            this.nazwa = nazwa;
            Main.getContainer().getHashMapRynkow().put(this.nazwa,this);
        }
    }

    public String getKraj() {
        return kraj;
    }

    public void setKraj(String kraj) {
        this.kraj = kraj;
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

    public void setAdres(Adres adres) {
        this.adres = adres;
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
    public double pobierzMarze(double kwota) {
        double marza = getMarzaOdTransakcji()/100;
        return kwota+(kwota*marza);
    }

    public void setMarzaOdTransakcji(double marzaOdTransakcji) {
        this.marzaOdTransakcji = marzaOdTransakcji;
    }

    public HashMap<String, Inwestycja> getHashMapInwestycji() {
        return hashMapInwestycji;
    }

    public void setHashMapInwestycji(HashMap<String, Inwestycja> hashMapInwestycji) {
        this.hashMapInwestycji = hashMapInwestycji;
    }
}

