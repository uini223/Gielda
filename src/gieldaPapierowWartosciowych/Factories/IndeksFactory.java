package gieldaPapierowWartosciowych.Factories;

import gield.Rynek;
import gieldaPapierowWartosciowych.model.GieldaPapierowWartosciowych;
import gieldaPapierowWartosciowych.model.Indeks;
import main.Main;

public class IndeksFactory {
    private Rynek rynek;

    public Indeks getIndeks(){
        Indeks indeks = new Indeks();
        indeks.setName(generateName());
        indeks.setRodzic((GieldaPapierowWartosciowych) rynek);
        indeks.setNajmniejszaWartosc(0);
        indeks.setNajwiekszaWartosc(0);
        addIndeksToContainer(indeks);
        return indeks;
    }

    private void addIndeksToContainer(Indeks indeks){
        synchronized (Main.getMonitor()){
            Main.getContainer().addIndeks(indeks);
        }
    }

    public Rynek getRynek() {
        return rynek;
    }

    public void setRynek(Rynek rynek) {
        this.rynek = rynek;
    }

    private String  generateName(){
        return "indeks" + Integer.toString((int)((Math.random())*10000));
    }
}
