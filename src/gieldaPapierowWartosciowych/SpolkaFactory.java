package gieldaPapierowWartosciowych;

import gield.Rynek;
import main.Main;

import java.util.Date;

public class SpolkaFactory {

    private Indeks indeks;
    private Rynek rynek;

    public Spolka getSpolka(){
        Spolka spolka = new Spolka();
        spolka.setRynek(rynek);
        spolka.setName(generateName());
        spolka.setDataPierwszejWyceny(new Date(Main.getContainer().getDate().getTime()));
        spolka.setKapitalZakladowy(Math.random()*100000);
        spolka.setKapitalWlasny(Math.random()*10000);
        spolka.setLiczbaAkcji((int)(Math.random()*1000000)%100001+50000);
        spolka.setPrzychod(Math.random()*1000);
        spolka.setZysk(spolka.getPrzychod()-(Math.random()*500));
        spolka.setWolumen(0);
        spolka.setObroty(0);
        spolka.getHashMapIndeksow().put(indeks.getName(),indeks);
        AkcjeFactory akcjeFactory = new AkcjeFactory();
        spolka.setAkcjaSpolki(akcjeFactory.getAkcje(spolka));
        addSpolkaToContainer(spolka);
        return spolka;
    }

    private String generateName(){
        String[] pierwszaCzesc = {"New", "Star", "Bez", "Odrey", "Dog", "Skoki", "music"};
        String[] drugaCzesc = {"Corp", "z o.o","sp.j", "company", "fundation", "studio", "Ehdb"};
        int zakresPierwszy = pierwszaCzesc.length;
        int zakresDrugi = drugaCzesc.length;
        return pierwszaCzesc[(int)(Math.random()*100)%zakresPierwszy]
                +drugaCzesc[(int)(Math.random()*100)%zakresDrugi]+Integer.toString(
                (int)(Math.random()*100));
    }

    private void addSpolkaToContainer(Spolka spolka){
        synchronized (Main.getMonitor()){
            Main.getContainer().addSpolka(spolka);
        }
    }
    public Indeks getIndeks() {
        return indeks;
    }

    public void setIndeks(Indeks indeks) {
        this.indeks = indeks;
    }

    public Rynek getRynek() {
        return rynek;
    }

    public void setRynek(Rynek rynek) {
        this.rynek = rynek;
    }
}
