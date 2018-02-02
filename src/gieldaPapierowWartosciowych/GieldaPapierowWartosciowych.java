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
        String[] waluty = {"PLN","GBP","USD","KYS","BTC"};
        int a = (int)((Math.random()*100)%waluty.length);
        synchronized (Main.getMonitor()) {
            setWaluta(Main.getContainer().getHashMapWalut().get(waluty[a]));
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
        hashMapIndeksow.put(ind.getNazwa(),ind);
        aktualizujIndeksy();
        aktualizujSpolki();
    }

    public void aktualizujSpolki(){
        for (String s :
                hashMapIndeksow.keySet()) {
            hashMapSpolek.putAll(hashMapIndeksow.get(s).getHashMapSpolek());
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
                    spolka.sprzedajAkcje(ilosc);
                    pp.setKapital(pp.getKapital()-kwota);
                    /*System.out.printf("Name: %s Kapital: %.2f Ilosc %d kwota zakupu %.2f nazwa %s\n"
                            ,pp.getName(), pp.getKapital(),ilosc,kwota,spolka.getName());
                    System.out.println("------------------------------"); */
                }
            }
            i++;
        }
    }

    private double pobierzMarze(double kwota) {
        double marza = getMarzaOdTransakcji()/100;
        return kwota-(kwota*marza);
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
                }
                pp.setKapital(pp.getKapital()+kwota);
                s.kupAkcje(ilosc);
            }
        }
    }

}
