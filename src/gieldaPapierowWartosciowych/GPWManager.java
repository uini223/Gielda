package gieldaPapierowWartosciowych;

import gield.Rynek;
import main.Main;

import java.util.HashSet;
import java.util.Set;

public class GPWManager {

    private GieldaPapierowWartosciowych gpw;

    public GPWManager(GieldaPapierowWartosciowych gpw) {
        this.gpw = gpw;
    }

    public GPWManager(){}

    /**
     * dodaje nowy indeks do giełdy z domyślną ilością nowych społek
     */
    public void dodajIndeks() throws Exception {
        if (gpw != null) {
            IndeksBuilder indeksBuilder = new IndeksBuilder();
            Indeks indeks = indeksBuilder.getIndeks(gpw);
            gpw.addIndeks(indeks);
            indeks.attachObserver(gpw.getGpwObserver());
        }
        else throw new Exception("Nie przypisano gieldy do zarzadzania!");
    }

    /**
     * dodaje nowy indeks do spółki z podaną ilością nowych społek
     * @param number ilość nowych społek
     */
    public void dodajIndeks(int number) throws Exception {
        if (gpw != null) {
            if (number >= 1) {
                IndeksBuilder indeksBuilder = new IndeksBuilder();
                indeksBuilder.setNumber(number);
                Indeks indeks = indeksBuilder.getIndeks(gpw);
                indeks.attachObserver(gpw.getGpwObserver());
            } else throw new Exception("Nieprawidłowa ilość spółek");
        }
        else throw new Exception("Nie przypisano gieldy do zarzadzania!");
    }

    /**
     * Dodaje nową spółkę do losowo wybranego indeksu
     */
    public void dodajNowaSpolke() throws Exception {
        if(gpw!=null) {
            if (gpw.getHashMapIndeksow().values().size() > 0) {
                SpolkaFactory spolkaFactory = new SpolkaFactory();
                spolkaFactory.setRynek(gpw);
                int rnd = (int) (Math.random() * gpw.getHashMapIndeksow().values().size());
                int a = 0;
                for (Indeks indeks :
                        gpw.getHashMapIndeksow().values()) {
                    if (a == rnd) {
                        Spolka spolka = spolkaFactory.getSpolka();
                        spolkaFactory.setIndeks(indeks);
                        indeks.dodajSpolke(spolka);
                        spolka.getAkcjaSpolki().attachObserver(indeks.getIndeksObserver());
                    }
                }
            } else throw new Exception("Nie ma indeksu do którego można by dodać spółkę");
        }
        else throw new Exception("Nie przypisano gieldy do zarządzania");
    }

    /**
     * dodaje nową spółkę do indeksu podanego jako parametr
     * @param indeks do którego zostanie dodana nowa spółka
     */
    public void dodajNowaSpolke(Indeks indeks) throws Exception {
        if(gpw!=null) {
            SpolkaFactory spolkaFactory = new SpolkaFactory();
            spolkaFactory.setRynek(gpw);
            spolkaFactory.setIndeks(indeks);
            Spolka spolka = spolkaFactory.getSpolka();
            indeks.dodajSpolke(spolka);
            spolka.getAkcjaSpolki().attachObserver(indeks.getIndeksObserver());
        }
        else throw new Exception("Nie przypisano gieldy do zarządzania");
    }

    /**
     * @param indeks indeks do którego ma zostać przypisana spółka
     * @param spolka istniejaca spółka która ma zostatać dodana do indeksu
     * @throws Exception
     */
    public void dodajSpolkeDoIndeksu(Indeks indeks,Spolka spolka) throws Exception{
        if(!indeks.getHashMapSpolek().containsValue(spolka)){
            indeks.getHashMapSpolek().put(spolka.getName(),spolka);
            spolka.getAkcjaSpolki().attachObserver(indeks.getIndeksObserver());
        }
        else{
            throw new Exception("Spółka już należy do tego indeksu!");
        }
    }

    /**
     * @param spolka usuwa podana w parametrze spolke z symulowanego swiata
     */
    public void usunSpolkeZeSwiata(Spolka spolka){
        for (Indeks indeks :
                spolka.getHashMapIndeksow().values()) {
            {
                indeks.getHashMapSpolek().remove(spolka.getName());
                spolka.getAkcjaSpolki().detachObserver(indeks.getIndeksObserver());
                if (indeks.getHashMapSpolek().values().size() == 0) {
                    usunDowiazaniaIndeksu(indeks);
                }
            }
        }
        spolka.getHashMapIndeksow().clear();
        if(spolka.getRynek() instanceof GieldaPapierowWartosciowych){
            ((GieldaPapierowWartosciowych) spolka.getRynek()).getHashMapSpolek().remove(spolka.getName());
        }
        synchronized (Main.getMonitor()){
            Main.getContainer().getHashMapSpolek().remove(spolka.getName());
        }
        //TODO zwrot kasy dla inwestorow
    }

    /**
     * @param spolka usuwa dowiązania spółki za wyjątkiem dowiązań do indeksów
     */
    private void usunDowiazaniaSpolki(Spolka spolka){
        if(spolka.getRynek() instanceof GieldaPapierowWartosciowych){
            ((GieldaPapierowWartosciowych) spolka.getRynek()).getHashMapSpolek().remove(spolka.getName());
            spolka.getAkcjaSpolki().detachObserver(((GieldaPapierowWartosciowych) spolka.getRynek()).getGpwObserver());
        }
        synchronized (Main.getMonitor()){
            Main.getContainer().getHashMapSpolek().remove(spolka.getName());
        }

    }

    /**
     * @param indeks usuwa dowiązania indeksu za wyjątkiem dowiązań do spółek
     */
    private void usunDowiazaniaIndeksu(Indeks indeks){
        synchronized (Main.getMonitor()){
            Main.getContainer().getHashMapIndeksow().remove(indeks.getName());
            if(indeks.getRynek() instanceof GieldaPapierowWartosciowych){
                ((GieldaPapierowWartosciowych) indeks.getRynek()).getHashMapIndeksow().
                        remove(indeks.getName());
                indeks.detachObserver(((GieldaPapierowWartosciowych) indeks.getRynek()).getGpwObserver());
            }
        }
    }

    /**
     * usuwa spolke podaną w parametrze z indeksu podanego w parametrze
     * @param indeks
     * @param spolka
     */
    private void usunSpolkeZIndeksu(Indeks indeks,Spolka spolka){
        indeks.getHashMapSpolek().remove(spolka.getName());
        spolka.getAkcjaSpolki().detachObserver(indeks.getIndeksObserver());
        spolka.getHashMapIndeksow().remove(indeks.getName());
        if(spolka.getHashMapIndeksow().size()==0){
            usunDowiazaniaSpolki(spolka);
        }
        if(indeks.getHashMapSpolek().size()==0){
            usunDowiazaniaIndeksu(indeks);
        }
    }

    /**
     * @param indeks usuwa indeks ze świata
     */
    public void usunIndeksZeSwiata(Indeks indeks){
        Set<Spolka> spolki = new HashSet<>(indeks.getHashMapSpolek().values());
        for (Spolka spolka :
                spolki) {
            usunSpolkeZIndeksu(indeks,spolka);
        }
    }

    /**
     * Usuwa gielde ze swiata
     */
    public void usunGieldeZeSwiata(){
        Set<Indeks> indeksy = new HashSet<>(gpw.getHashMapIndeksow().values());
        for (Indeks indeks :
                indeksy) {
            usunIndeksZeSwiata(indeks);
        }
        synchronized (Main.getMonitor()){
            Main.getContainer().getHashMapRynkow().remove(gpw.getNazwa());
        }
        gpw = null;
    }

    public GieldaPapierowWartosciowych getGpw() {
        return gpw;
    }

    public void setGpw(GieldaPapierowWartosciowych gpw) {
        this.gpw = gpw;
    }
}
