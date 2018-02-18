package rynekwalut;

import gield.Adres;
import gield.Inwestycja;
import gieldaPapierowWartosciowych.Indeks;
import gield.Rynek;
import main.Main;
import posiadajacyPieniadze.PosiadajacyPieniadze;

import java.util.HashMap;
import java.util.List;

/**
 *  klasa dla rynku walut
 */
public class RynekWalut extends Rynek {

    private HashMap<String,Waluta> hashMapWalut;

    /**
     * konstruktor
     */
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
    public void addNewWaluta() {
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
                Main.getContainer().getWalutaSet().remove(waluta);
                waluta.setRynek(this);
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
                Waluta waluta = hashMapWalut.get(s);
                int ilosc = (int)(Math.random()*10000);
                double kwota = ilosc*waluta.getAktualnaWartosc();
                kwota = pobierzMarze(kwota);
                kupno(kwota,ilosc,pp,waluta);
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
        sprzedaz(kwota,ileSprzedanych,pp,inwestycja);
    }

    public HashMap<String, Waluta> getHashMapWalut() {
        return hashMapWalut;
    }

}
