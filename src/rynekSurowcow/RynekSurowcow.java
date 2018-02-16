package rynekSurowcow;

import gield.Adres;
import gield.Inwestycja;
import gield.Rynek;
import gieldaPapierowWartosciowych.Indeks;
import main.Main;
import posiadajacyPieniadze.PosiadajacyPieniadze;
import rynekwalut.Waluta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RynekSurowcow extends Rynek{
    private Map<String,Surowiec> hashMapSurowcow;

    public RynekSurowcow() {
        super();
        hashMapSurowcow = new HashMap<>();
        for(int i=0;i<3;i++){
            addSurowiec();
        }
    }

    public Map<String, Surowiec> getHashMapSurowcow() {
        return hashMapSurowcow;
    }

    public void addSurowiec(){
        synchronized (Main.getMonitor()){
            int size = Main.getContainer().getSurowiecSet().size();
            if(size>0) {
                int a = (int) (Math.random() * 10000) % size;
                int n=0;
                Surowiec x=null;
                for (Surowiec surowiec :
                        Main.getContainer().getSurowiecSet()) {
                    if (a == n) {
                        hashMapSurowcow.put(surowiec.getNazwa(),surowiec);
                        surowiec.setRynek(this);
                        x = surowiec;
                    }
                    n++;
                }
                if(x!=null) Main.getContainer().getSurowiecSet().remove(x);
            }
            else{
                Surowiec surowiec = Main.getContainer().addNewSurowiec();
                hashMapSurowcow.put(surowiec.getNazwa(), surowiec);
                surowiec.setRynek(this);
                Main.getContainer().getSurowiecSet().remove(surowiec);
            }
        }
    }

    public void setHashMapSurowcow(Map<String, Surowiec> hashMapSurowcow) {
        this.hashMapSurowcow = hashMapSurowcow;
    }

    @Override
    public void kupno(PosiadajacyPieniadze pp) {
        Surowiec surowiec=null;
        int ilosc = (int)(Math.random()*1000);
        int size = hashMapSurowcow.values().size();
        int a=0;
        if(size>0)  a = (int)(Math.random()*10000)%size;
        int n=0;
        for (Surowiec s :
                hashMapSurowcow.values()) {
            if(a==n){
                surowiec = s;
            }
            n++;
        }
        if(surowiec!=null){
            double kwota = ilosc*surowiec.getWaluta().getPrzelicznik();
            kwota = pobierzMarze(kwota);
            int pom;
            if(kwota<= pp.getKapital() && ilosc>0){
                if(pp.getHashMapInwestycji().containsKey(surowiec)){
                    pom = pp.getHashMapInwestycji().get(surowiec).intValue();
                    pp.getHashMapInwestycji().put(surowiec,pom+ilosc);
                }
                else {
                    pp.getHashMapInwestycji().put(surowiec,ilosc);
                }
                pp.setKapital(pp.getKapital()-kwota);
                surowiec.getSetInwestorow().add(pp);
                zmienKurs(surowiec);
                surowiec.addWartoscAkcji(surowiec.getAktualnaWartosc());
            }
        }
    }
    private void zmienKurs(Surowiec s){
        synchronized (Main.getMonitor()) {
            double old = s.getAktualnaWartosc();
            int rnd = (int) (Math.random() * 100) % 10 + 1;
            double wartosc = (old * ((double) rnd / 100));
            int plus = (int) (Math.random() * 100);
            if (plus % 2 == 0) {
                s.setAktualnaWartosc(old + wartosc);
            } else {
                s.setAktualnaWartosc(old - wartosc);
            }
            if (s.getAktualnaWartosc() > s.getNajwiekszaWartosc()) {
                s.setNajwiekszaWartosc(s.getAktualnaWartosc());
            }
            if (s.getNajwiekszaWartosc() < s.getNajmniejszaWartosc()) {
                s.setNajmniejszaWartosc(s.getAktualnaWartosc());
            }
        }
    }
    @Override
    public void sprzedaz(Inwestycja inwestycja,PosiadajacyPieniadze pp) {
        Surowiec surowiec = (Surowiec) inwestycja;
        int size = (int) pp.getHashMapInwestycji().get(surowiec);
        int ilosc = (int) (Math.random()*10000)%size+1;
        double kwota = ilosc*surowiec.getWaluta().getPrzelicznik();
        pobierzMarze(kwota);
        if(size==ilosc){
            pp.getHashMapInwestycji().remove(surowiec);
            surowiec.getSetInwestorow().remove(pp);
        }
        else {
            pp.getHashMapInwestycji().put(surowiec,size-ilosc);
        }
        pp.setKapital(pp.getKapital()+kwota);
    }

}
