package rynekSurowcow;

import gield.Adres;
import gield.Inwestycja;
import gield.Rynek;
import gieldaPapierowWartosciowych.Indeks;
import posiadajacyPieniadze.PosiadajacyPieniadze;
import rynekwalut.Waluta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RynekSurowcow extends Rynek{
    private Map<String,Surowiec> hashMapSurowcow;

    public Map<String, Surowiec> getHashMapSurowcow() {
        return hashMapSurowcow;
    }

    public void addSurowiec(Surowiec s){
        hashMapSurowcow.putIfAbsent(s.getNazwa(),s);
    }

    public void setHashMapSurowcow(Map<String, Surowiec> hashMapSurowcow) {
        this.hashMapSurowcow = hashMapSurowcow;
    }

    public RynekSurowcow() {
        super();
        hashMapSurowcow = new HashMap<>();

    }

    @Override
    public void kupno(PosiadajacyPieniadze pp) {

    }

    @Override
    public void sprzedaz(Inwestycja inwestycja,PosiadajacyPieniadze pp) {

    }

}
