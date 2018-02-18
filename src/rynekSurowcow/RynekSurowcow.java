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

/**
 * klasa dla rynku surowców
 */
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

    /**
     * dodaje losowy surowiec do rynku (najpierw szuka "wonly" jezeli nie znajdzie to tworzy zupelnie nowy sutowiec
     */
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


    /**
     * @param pp ten, ktory kupuje na rynku
     *           metoda kupowania z rynu
     */
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
            double kwota = ilosc*surowiec.getAktualnaWartosc();
            kwota = pobierzMarze(kwota);
            kwota=kwota*surowiec.getWaluta().getAktualnaWartosc();
            kupno(kwota,ilosc,pp,surowiec);
        }
    }

    /**
     * @param inwestycja sprzedawana inwestycja
     * @param pp ten co sprzedaje
     *           metoda sprzedazy inwestycji na rynku
     */
    @Override
    public void sprzedaz(Inwestycja inwestycja,PosiadajacyPieniadze pp) {
        Surowiec surowiec = (Surowiec) inwestycja;
        int size = (int) pp.getHashMapInwestycji().get(surowiec);
        int ilosc = (int) (Math.random()*10000)%size+1;
        double kwota = ilosc*surowiec.getAktualnaWartosc();
        pobierzMarze(kwota);
        kwota = kwota*surowiec.getWaluta().getPrzelicznik();
        sprzedaz(kwota,ilosc,pp,inwestycja);
    }

}
