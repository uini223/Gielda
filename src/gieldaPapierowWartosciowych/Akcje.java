package gieldaPapierowWartosciowych;

import gield.Inwestycja;
import observers.Observer;

import java.util.List;

/**
 * klasa dla akcji spolki
 */
public class Akcje extends Inwestycja{

    private Spolka spolka;


    /**
     * @param nazwaSpolki
     * @param aktualnaWartosc
     * konstruktor akcji
     */
    public Akcje(String nazwaSpolki, double aktualnaWartosc) {
        super(nazwaSpolki,aktualnaWartosc);
    }

    public Akcje(){

    }

    public String getNazwaSpolki() {
        return getName();
    }

    @Override
    public String toString() {
        return getName();
    }

    public Spolka getSpolka() {
        return spolka;
    }

    public void setSpolka(Spolka spolka) {
        this.spolka = spolka;
    }


}
