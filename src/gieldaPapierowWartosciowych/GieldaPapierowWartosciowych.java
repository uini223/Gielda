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

    public GieldaPapierowWartosciowych() {
        super();
        hashMapIndeksow = new HashMap<>();
        hashMapSpolek = new ConcurrentHashMap<>();
        Indeks ind;
        for(int i=0;i<3;i++){
            ind = new Indeks(this);
            hashMapIndeksow.put(ind.getName(),ind);
        }
        aktualizujIndeksy();
        //aktualizujSpolki();
    }

    public HashMap<String, Indeks> getHashMapIndeksow() {
        return hashMapIndeksow;
    }


    public void aktualizujIndeksy(){
        for (String s:hashMapIndeksow.keySet()
             ) {
            synchronized(Main.getContainer()){
                Main.getContainer().getHashMapIndeksow().put(s,hashMapIndeksow.get(s));
            }
        }
    }
    public void addIndeks(Indeks ind){
        hashMapIndeksow.put(ind.getName(),ind);
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
                pp.getListaInwestycji().add(spolka.getAkcjaSpolki());
                spolka.sprzedajAkcje();
            }
            i++;
        }
    }

    @Override
    public void sprzedaz(PosiadajacyPieniadze pp) {

    }

}
