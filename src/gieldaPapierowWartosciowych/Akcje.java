package gieldaPapierowWartosciowych;

import gield.Inwestycja;
import gield.Rynek;

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

    public String getNazwaSpolki() {
        return getNazwa();
    }

    @Override
    public String toString() {
        return getNazwa();
    }

    public Spolka getSpolka() {
        return spolka;
    }

    public void setSpolka(Spolka spolka) {
        this.spolka = spolka;
    }
}
