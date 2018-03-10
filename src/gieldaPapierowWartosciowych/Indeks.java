package gieldaPapierowWartosciowych;

import controllers.Listable;
import gield.Inwestycja;
import gield.Rynek;
import main.Main;
import observers.IndeksObserver;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * klasa dla indeksow spolek na GPW
 */
public class Indeks extends Inwestycja implements Listable, Serializable {

    private GieldaPapierowWartosciowych rodzic;
    private Map<String,Spolka> hashMapSpolek;
    private IndeksObserver indeksObserver;

    /**
     * @param rodzic rynek do ktorego nalezy indeks
     * tworzy nazwe indeksu i 3 nowe spolki, ktore do niego naleza (indeks nie moze istniec bez spolek)
     */
    public Indeks(GieldaPapierowWartosciowych rodzic) {
        super( "indeks" + Integer.toString((int)((Math.random())*10000)),0);
        //name = "indeks" + Integer.toString((int)((Math.random())*10000));
        this.rodzic = rodzic;
        setRynek(rodzic);
        Spolka spolka;
        for (int i = 0; i < 3; i++) {
            spolka = new Spolka(rodzic);
//            spolka.getHashMapIndeksow().add(this);
            Main.getContainer().getHashMapSpolek().put(spolka.getName(),spolka);
            hashMapSpolek.put(spolka.getName(),spolka);
            //spolka.getAkcjaSpolki().addWartoscAkcji(spolka.getAktualnyKurs());
            Thread th = new Thread(spolka);
            th.setDaemon(true);
            th.start();
        }
        rodzic.addIndeks(this);

    }
    public Indeks(){
        hashMapSpolek = new HashMap<>();
        //indeksObserver = new IndeksObserver(this);
    }

    @Override
    public String toString() {
        return getName();
    }

    public GieldaPapierowWartosciowych getRodzic() {
        return rodzic;
    }

    public void setRodzic(GieldaPapierowWartosciowych rodzic) {
        this.rodzic = rodzic;
    }

    public int getParametrDoboruSpolek() {
        return parametrDoboruSpolek;
    }

    public void setParametrDoboruSpolek(int parametrDoboruSpolek) {
        this.parametrDoboruSpolek = parametrDoboruSpolek;
    }

    private int parametrDoboruSpolek;

    /**
     * @param a dodaje spolke 'a' do zestawu spolek indeksu
     */
    public void dodajSpolke(Spolka a){
        hashMapSpolek.put(a.getName(),a);
        a.addIndeks(this);
        notifyAllObservers();
    }

    /**
     * @param a parametr sluzy to wyboru trybu aktualizowania wartosci
     * a == 0 przy inicjalizacji wartosc
     *          else aktualizuje wartosc indeksu na podstawie sumy wartosci spolek nalezacych do indeksu
     */
    public void aktualizujWartosc(){
        double wartosc = 0;
        for (String s :
                hashMapSpolek.keySet()) {
            wartosc+=hashMapSpolek.get(s).getAkcjaSpolki().getAktualnaWartosc();
        }
    }
    @Override
    public Rynek getRynek(){
        return rodzic;
    }

    /**
     * @param a usuwa spolke 'a' z indeksu
     */
    public void usunSpolke(Spolka a){
        hashMapSpolek.remove(a.getName(),a);
        rodzic.aktualizujSpolki();
    }

    public IndeksObserver getIndeksObserver() {
        return indeksObserver;
    }

    public void setIndeksObserver(IndeksObserver indeksObserver) {
        this.indeksObserver = indeksObserver;
    }

    public Map<String, Spolka> getHashMapSpolek() {
        return hashMapSpolek;
    }

    public void setHashMapSpolek(Map<String, Spolka> hashMapSpolek) {
        this.hashMapSpolek = hashMapSpolek;
    }


}
