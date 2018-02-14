package gieldaPapierowWartosciowych;

import gield.Inwestycja;
import gield.Rynek;

public class Akcje extends Inwestycja{

    public Akcje(Rynek rynek, String nazwaSpolki, double aktualnaWartosc) {
        super(nazwaSpolki,aktualnaWartosc);
    }

    public String getNazwaSpolki() {
        return getNazwa();
    }

    @Override
    public String toString() {
        return getNazwa();
    }
}
