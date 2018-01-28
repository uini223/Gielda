package gieldaPapierowWartosciowych;

import gield.Adres;
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
//        for (String s :
//                hashMapSpolek.keySet()) {
//            System.out.println(s);
//        }
//        System.out.println("___________");
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
                    System.out.println(spolka.getAkcjaSpolki().getWartosciAkcji());
                    pp.setKapital(pp.getKapital()-kwota);
                    System.out.printf("Name: %s Kapital: %.2f Ilosc %d kwota zakupu %.2f nazwa %s\n"
                            ,pp.getName(), pp.getKapital(),ilosc,kwota,spolka.getName());
                    System.out.println("------------------------------");
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
    public void sprzedaz(PosiadajacyPieniadze pp) {

    }

}
