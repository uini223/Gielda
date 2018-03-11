package gieldaPapierowWartosciowych;

import gield.Rynek;
import gieldaPapierowWartosciowych.Factories.IndeksFactory;
import gieldaPapierowWartosciowych.Factories.SpolkaFactory;
import gieldaPapierowWartosciowych.model.Indeks;
import observers.IndeksObserver;

public class IndeksBuilder {

    private IndeksFactory indeksFactory = new IndeksFactory();
    private SpolkaFactory spolkaFactory = new SpolkaFactory();
    private int number = 3;

    public Indeks getIndeks(Rynek rynek){
        indeksFactory.setRynek(rynek);
        Indeks indeks = indeksFactory.getIndeks();
        spolkaFactory.setRynek(rynek);
        spolkaFactory.setIndeks(indeks);
        for(int i=0;i<number;i++){
            indeks.dodajSpolke(spolkaFactory.getSpolka());
        }
        indeks.setIndeksObserver(new IndeksObserver(indeks));
        return indeks;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
