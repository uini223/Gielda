package gieldaPapierowWartosciowych;

import gield.Adres;
import gield.Rynek;
import main.Main;
import rynekwalut.Waluta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GieldaPapierowWartosciowych extends Rynek{

    private HashMap<String,Indeks> hashMapIndeksow;

    public GieldaPapierowWartosciowych() {
        super();
        hashMapIndeksow = new HashMap<>();
        Indeks ind;
        for(int i=0;i<3;i++){
            ind = new Indeks(this);
            hashMapIndeksow.put(ind.getName(),ind);
        }
        aktualizujIndeksy();
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
    }

    public void aktualizujSpolki(){

    }

    @Override
    public void kupno() {

    }

    @Override
    public void sprzedaz() {

    }
}
