package gield;

import controllers.Listable;
import gieldaPapierowWartosciowych.Indeks;
import main.Main;
import rynekwalut.Waluta;

import java.util.Set;

public abstract class Rynek implements Listable{
    private String nazwa, kraj;
    private Waluta waluta;
    private Adres adres;
    private Indeks indeks;
    private double marzaOdTransakcji;

    public Rynek() {
        String[] nazwyGield = {"GPW","NYM","WSP","PPP","APS","STH","UNE","OMA","ead","sdada","dada"};
        for (int i=0;i<nazwyGield.length;i++) {
            nazwyGield[i] += Integer.toString((int)(Math.random()*1000));
        }

        String[] waluty = {"PLN","GBP","USD","KYS","BTC"};
        int a = (int)((Math.random()*100)%waluty.length);
        nazwa = nazwyGield[(int)(Math.random()*100)%nazwyGield.length];
        this.kraj = "kraj";

        synchronized (Main.getContainer()) {
          this.waluta = Main.getContainer().getHashMapWalut().get(waluty[a]);
        }
        this.adres = new Adres();
        this.marzaOdTransakcji = 0.5;
    }

    public abstract void kupno();

    public abstract void sprzedaz();

    @Override
    public String toString() {
        return "Rynek{" +
                "nazwa='" + nazwa + '\'' +
                ", kraj='" + kraj + '\'' +
                ", waluta=" + waluta +
                ", adres=" + adres +
                ", indeks=" + indeks +
                ", marzaOdTransakcji=" + marzaOdTransakcji +
                '}';
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

    public void setMarzaOdTransakcji(double marzaOdTransakcji) {
        this.marzaOdTransakcji = marzaOdTransakcji;
    }
}

