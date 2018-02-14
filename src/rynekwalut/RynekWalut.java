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
    public void addNewWaluta() {
        boolean znaleziony = false;
        synchronized (Main.getMonitor()) {
            for (String s :
                    Main.getContainer().getHashMapWalut().keySet()) {
                if (!hashMapWalut.containsKey(s) && !s.equals("PLN")) {
                    hashMapWalut.put(s, Main.getContainer().getHashMapWalut().get(s));
                    znaleziony = true;
                    break;
                }
            }
        }
        if(!znaleziony){
            synchronized (Main.getMonitor()) {
                Waluta waluta = Main.getContainer().addNewWaluta();
                hashMapWalut.put(waluta.getNazwa(), waluta);
            }
        }
    }
    @Override
    public void kupno(PosiadajacyPieniadze pp) {

    }

    @Override
    public void sprzedaz(Inwestycja inwestycja, PosiadajacyPieniadze pp) {

    }

}
