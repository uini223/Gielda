package gieldaPapierowWartosciowych;

import gield.Inwestycja;
import gield.Rynek;

public class Akcje extends Inwestycja{
    private Spolka spolka;

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
