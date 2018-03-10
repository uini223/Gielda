package gieldaPapierowWartosciowych;

import gield.Inwestycja;
import gield.Rynek;
import main.Main;
import posiadajacyPieniadze.PosiadajacyPieniadze;

import java.util.HashMap;
import java.util.Map;

/**
 *  klasa dla GPW
 */
public class GieldaPapierowWartosciowych extends Rynek{

    private HashMap<String,Indeks> hashMapIndeksow;

    private Map<String, Spolka> hashMapSpolek;

    public Map<String, Spolka> getHashMapSpolek() {
        return hashMapSpolek;
    }

    /**
     * konstruktor GPW, tworzy nowy indeks (gpw nie moze istiec bez indeksu)
     */
    public GieldaPapierowWartosciowych() {
        super();


        hashMapIndeksow = new HashMap<>();
        hashMapSpolek = new HashMap<>();
        Indeks ind;
        for(int i=0;i<1;i++){
            ind = new Indeks(this);
            hashMapIndeksow.put(ind.getName(),ind);
        }
        aktualizujIndeksy();
    }

    public HashMap<String, Indeks> getHashMapIndeksow() {
        return hashMapIndeksow;
    }


    /**
     * aktualizuje indeksy w kontenerze
     */
    public void aktualizujIndeksy(){
        synchronized (Main.getMonitor()) {
            Main.getContainer().getHashMapIndeksow().putAll(hashMapIndeksow);
        }
    }

    /**
     * @param ind dodaje indeks do gieldy
     */
    public void addIndeks(Indeks ind){
        synchronized (Main.getMonitor()) {
            hashMapIndeksow.put(ind.getName(), ind);
            aktualizujIndeksy();
            aktualizujSpolki();
        }
    }

    /**
     * aktualizuje spolki z ineksow
     */
    public void aktualizujSpolki(){
        synchronized (Main.getMonitor()) {
            for (Indeks s :
                    hashMapIndeksow.values()) {
                hashMapSpolek.putAll(s.getHashMapSpolek());
            }
        }
    }

    /**
     * @param pp inwestor ktory kupuje na danym rynku
     * funkcja odpowiedzialna za wybranie losowej spolki z rynku i dokonania zakupu jej akcji
     */
    @Override
    public void kupno(PosiadajacyPieniadze pp) {
        aktualizujSpolki();
        Spolka spolka;
        int size = hashMapSpolek.keySet().size(),i=0,rnd;
        rnd = (int) (Math.random()*1000)%(size+1);
        for (String s :
                hashMapSpolek.keySet()) {
            if (i == rnd){
                spolka = hashMapSpolek.get(s);
                int pom;
                int ilosc = (int)(Math.random()*1000)%spolka.getLiczbaAkcji();
                double kwota = ilosc*spolka.getAkcjaSpolki().getAktualnaWartosc();
                kwota = pobierzMarze(kwota);
                kwota = getWaluta().getAktualnaWartosc()*kwota;
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


    /**
     * @param inwestycja sprzedawana inwestycja
     * @param pp sprzedajacy inwestor
     * inwestor sprzedaje akcje inwestycji
     */
    @Override
    public void sprzedaz(Inwestycja inwestycja,PosiadajacyPieniadze pp) {
        Spolka s;
        double kwota;
        if(inwestycja instanceof Akcje){
            s = hashMapSpolek.get(((Akcje) inwestycja).getNazwaSpolki());
            int ilosc = (int) pp.getHashMapInwestycji().get(inwestycja);
            ilosc = (int)(Math.random()*10000)%ilosc;
            kwota = ilosc *s.getAkcjaSpolki().getAktualnaWartosc();
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

    /**
     * dodaje nowa spolke do rynki
     */
    public void addNewSpolka() {
        int rnd = (int)(Math.random()*10000)%hashMapIndeksow.size();
        int n=0;
        for(Indeks indeks:
                hashMapIndeksow.values()){
            if(rnd==n){
                Spolka spolka = new Spolka(this);
                indeks.dodajSpolke(spolka);
                Thread thread= new Thread(spolka);
                thread.setDaemon(false);
                thread.start();
            }
            n++;
        }
    }
}
