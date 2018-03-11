package gieldaPapierowWartosciowych.model;

import gield.Inwestycja;

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
        super();
    }

    public String getNazwaSpolki() {
        return getName();
    }

    public Spolka getSpolka() {
        return spolka;
    }

    public void setSpolka(Spolka spolka) {
        this.spolka = spolka;
    }

    @Override
    public String toString() {
        return getName();
    }
}
