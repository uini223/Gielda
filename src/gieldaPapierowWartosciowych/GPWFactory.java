package gieldaPapierowWartosciowych;

import main.Main;
import rynekwalut.Waluta;

public class GPWFactory {


    public GieldaPapierowWartosciowych getGPW(){
        GieldaPapierowWartosciowych gpw = new GieldaPapierowWartosciowych();
        gpw.setWaluta(losujWalute());
        return gpw;

    }

    private Waluta losujWalute(){
        Waluta w = null;
        synchronized (Main.getMonitor()) {
            int a = (int)((Math.random()*1000)%Main.getContainer().getHashMapWalut().size());
            int n=0;
            for (Waluta waluta:
                    Main.getContainer().getHashMapWalut().values()) {
                if(a==n){
                    w = waluta;
                }
                n++;
            }
        }
        return w;
    }
}
