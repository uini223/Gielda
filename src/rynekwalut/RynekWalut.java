package rynekwalut;

import gield.Adres;
import gield.Inwestycja;
import gieldaPapierowWartosciowych.Indeks;
import gield.Rynek;
import main.Main;
import posiadajacyPieniadze.PosiadajacyPieniadze;

import java.util.HashMap;
import java.util.List;

public class RynekWalut extends Rynek {

    private HashMap<String,Waluta> hashMapWalut;

    public RynekWalut() {
        super();
        hashMapWalut = new HashMap<>();
        for(int i=0;i<3;i++){
            addNewWaluta();
        }
        synchronized (Main.getMonitor()) {
            this.setWaluta(Main.getContainer().getHashMapWalut().get("PLN"));
        }
    }

    /**
     * Dodaje nową walute do rynku
     */
    private void addNewWaluta() {
       synchronized (Main.getMonitor()){
            int size = Main.getContainer().getWalutaSet().size();
            if(size>0) {
                int a = (int) (Math.random() * 10000) % size;
                int n=0;
                Waluta x=null;
                for (Waluta waluta :
                        Main.getContainer().getWalutaSet()) {
                    if (a == n) {
                        hashMapWalut.put(waluta.getNazwa(),waluta);
                        waluta.setRynek(this);
                        x = waluta;
                    }
                    n++;
                }
                if(x!=null) Main.getContainer().getWalutaSet().remove(x);
            }
            else{
                Waluta waluta = Main.getContainer().addNewWaluta();
                hashMapWalut.put(waluta.getNazwa(), waluta);
                waluta.setRynek(this);
            }

       }
    }

    public HashMap<String, Waluta> getHashMapWalut() {
        return hashMapWalut;
    }

    public void setHashMapWalut(HashMap<String, Waluta> hashMapWalut) {
        this.hashMapWalut = hashMapWalut;
    }

    /**
     * @param waluta
     * @param pp
     * Funkcja pomocnicza do operacji zakupu na rynku walut
     */
    private void kup(Waluta waluta,PosiadajacyPieniadze pp){
        int ilosc = (int)(Math.random()*10000);
        double kwota = ilosc*waluta.getPrzelicznik();
        int pom;
        kwota = pobierzMarze(kwota);
        if( kwota <= pp.getKapital() && ilosc>0) {
            if(pp.getHashMapInwestycji().containsKey(waluta)) {
                pom = pp.getHashMapInwestycji().get(waluta).intValue();
                pp.getHashMapInwestycji().put(waluta,pom+ilosc);
            }
            else {
                pp.getHashMapInwestycji().put(waluta,ilosc);
            }
            pp.setKapital(pp.getKapital()-kwota);
            waluta.getSetInwestorow().add(pp);
            zmienKurs(waluta);
            waluta.addWartoscAkcji(waluta.getPrzelicznik());
        }
    }


    /**
     * @param waluta
     * ustala nowy kurs po zakupie
     */
    private void zmienKurs(Waluta waluta){
        synchronized (Main.getMonitor()) {
            double old = waluta.getPrzelicznik();
            int rnd = (int) (Math.random() * 100) % 10 + 1;
            double wartosc = (old * ((double) rnd / 100));
            int plus = (int) (Math.random() * 100);
            if (plus % 2 == 0) {
                waluta.setPrzelicznik(old + wartosc);
            } else {
                waluta.setPrzelicznik(old - wartosc);
            }
        }
    }

    /**
     * @param pp
     * metoda wywoływana przez inwestorow/fundusze do zakupu losowego towaru z rynku
     */
    @Override
    public void kupno(PosiadajacyPieniadze pp) {
        int a =(int)(Math.random()*1000)%hashMapWalut.keySet().size();
        int n=0;
        for (String s :
                hashMapWalut.keySet()) {
            if (a == n) {
                kup(hashMapWalut.get(s),pp);
            }
            n++;
        }
    }

    /**
     * @param inwestycja
     * @param pp
     * metoda wywoływana przez inwestorow/fundusze do sprzedazy posiadanego aktywa, ktore jest w tym rynku
     */
    @Override
    public void sprzedaz(Inwestycja inwestycja, PosiadajacyPieniadze pp) {
        Waluta waluta = (Waluta) inwestycja;
        int ilosc = (int) pp.getHashMapInwestycji().get(inwestycja);
        int ileSprzedanych = (int) (Math.random()*10000)%ilosc+1;
        double kwota = ileSprzedanych*waluta.getPrzelicznik();
        kwota = pobierzMarze(kwota);
        if(ileSprzedanych==ilosc){
            pp.getHashMapInwestycji().remove(inwestycja);
            waluta.getSetInwestorow().remove(pp);
        }
        else{
            pp.getHashMapInwestycji().put(inwestycja,ilosc-ileSprzedanych);
        }
        pp.setKapital(pp.getKapital()+kwota);
    }

}
