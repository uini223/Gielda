package gieldaPapierowWartosciowych;

import gield.Adres;
import main.Main;
import rynekwalut.Waluta;

public class GPWFactory {


    public GieldaPapierowWartosciowych getGPW(){
        GieldaPapierowWartosciowych gpw = new GieldaPapierowWartosciowych();
        gpw.setWaluta(losujWalute());
        gpw.setNazwa(losujNazwe());
        gpw.setKraj(losujKraj());
        gpw.setMarzaOdTransakcji(Math.random());
        gpw.setAdres(new Adres("Miasto","Lakowa",10));
        addRynekToContainer(gpw);
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

    private String losujNazwe(){
        return "GPW"+Math.random()*1000;
    }

    private String losujKraj(){
        String[] kraje= {"Polska","Ukraina","Rosja","Szwecja","Niemcy","USA","Kanada","Japonia"};
        return kraje[(int)(Math.random()*kraje.length)];
    }

    private void addRynekToContainer(GieldaPapierowWartosciowych gpw){
        synchronized (Main.getMonitor()){
            Main.getContainer().addRynek(gpw);
        }
    }
}
