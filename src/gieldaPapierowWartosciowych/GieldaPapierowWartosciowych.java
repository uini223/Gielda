package gieldaPapierowWartosciowych;

import gield.Adres;
import gield.Inwestycja;
import gield.Rynek;
import main.Main;
import posiadajacyPieniadze.PosiadajacyPieniadze;
import rynekwalut.Waluta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GieldaPapierowWartosciowych extends Rynek{

    private HashMap<String,Indeks> hashMapIndeksow;

    private Map<String, Spolka> hashMapSpolek;

    public Map<String, Spolka> getHashMapSpolek() {
        return hashMapSpolek;
    }

    public void setHashMapSpolek(Map<String, Spolka> hashMapSpolek) {
        this.hashMapSpolek = hashMapSpolek;
    }

    public GieldaPapierowWartosciowych() {
        super();

        synchronized (Main.getMonitor()) {
            int a = (int)((Math.random()*1000)%Main.getContainer().getHashMapWalut().size());
            int n=0;
            for (String s :
                    Main.getContainer().getHashMapWalut().keySet()) {
                if(a==n){
                    setWaluta(Main.getContainer().getHashMapWalut().get(s));
                }
                n++;
            }
        }
        hashMapIndeksow = new HashMap<>();
        hashMapSpolek = new HashMap<>();
        Indeks ind;
        for(int i=0;i<1;i++){
            ind = new Indeks(this);
            hashMapIndeksow.put(ind.getNazwa(),ind);
        }
        aktualizujIndeksy();

        //aktualizujSpolki();
    }

    public HashMap<String, Indeks> getHashMapIndeksow() {
        return hashMapIndeksow;
    }


    public void aktualizujIndeksy(){
        synchronized (Main.getMonitor()) {
            Main.getContainer().getHashMapIndeksow().putAll(hashMapIndeksow);
        }
    }

    public void addIndeks(Indeks ind){
        synchronized (Main.getMonitor()) {
            hashMapIndeksow.put(ind.getNazwa(), ind);
            aktualizujIndeksy();
            aktualizujSpolki();
        }
    }

    public void aktualizujSpolki(){
        synchronized (Main.getMonitor()) {
            for (Indeks s :
                    hashMapIndeksow.values()) {
                hashMapSpolek.putAll(s.getHashMapSpolek());
            }
        }
    }

    @Override
    public void kupno(PosiadajacyPieniadze pp) {
        aktualizujSpolki();
        Spolka spolka;
        int size = hashMapSpolek.keySet().size(),i=0,rnd;
        rnd = (int) (Math.random()*1000)%size;
        for (String s :
                hashMapSpolek.keySet()) {
            if (i == rnd){
                spolka = hashMapSpolek.get(s);
                int pom;
                int ilosc = (int)(Math.random()*1000000)%spolka.getLiczbaAkcji();
                double kwota = ilosc*spolka.getAktualnyKurs();
                kwota = getWaluta().przeliczCene(kwota);
                kwota = pobierzMarze(kwota);
                if( kwota <= pp.getKapital() && ilosc>0) {
                    if(pp.getHashMapInwestycji().containsKey(spolka.getAkcjaSpolki())) {
                        pom = pp.getHashMapInwestycji().get(spolka.getAkcjaSpolki()).intValue();
                        pp.getHashMapInwestycji().put(spolka.getAkcjaSpolki(),pom+ilosc);
                    }
                    else {
                        pp.getHashMapInwestycji().put(spolka.getAkcjaSpolki(),ilosc);
                    }
                    spolka.sprzedajAkcje(ilosc,kwota);
                    pp.setKapital(pp.getKapital()-kwota);
                    spolka.getAkcjaSpolki().getSetInwestorow().add(pp);
                }
            }
            i++;
        }
    }



    @Override
    public void sprzedaz(Inwestycja inwestycja,PosiadajacyPieniadze pp) {
        Spolka s;
        double kwota;
        if(inwestycja instanceof Akcje){
            s = hashMapSpolek.get(((Akcje) inwestycja).getNazwaSpolki());
            int ilosc = (int) pp.getHashMapInwestycji().get(inwestycja);
            ilosc = (int)(Math.random()*10000)%ilosc;
            kwota = ilosc *s.getAktualnyKurs();
            kwota = getWaluta().przeliczCene(kwota);
            kwota = pobierzMarze(kwota);
            if(ilosc>0){
                if(ilosc == (int)pp.getHashMapInwestycji().get(inwestycja)){
                    pp.getHashMapInwestycji().remove(inwestycja);
                    inwestycja.getSetInwestorow().remove(pp);
                }
                pp.setKapital(pp.getKapital()+kwota);
                s.kupAkcje(ilosc,kwota);
            }
        }
    }

}
