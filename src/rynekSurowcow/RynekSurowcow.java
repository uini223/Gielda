package rynekSurowcow;

import gield.Adres;
import gield.Inwestycja;
import gield.Rynek;
import gieldaPapierowWartosciowych.Indeks;
import posiadajacyPieniadze.PosiadajacyPieniadze;
import rynekwalut.Waluta;

import java.util.List;

public class RynekSurowcow extends Rynek{
    private List<Surowiec> listaSurowcow;
    public RynekSurowcow() {
        super();
    }

    @Override
    public void kupno(PosiadajacyPieniadze pp) {

    }

    @Override
    public void sprzedaz(Inwestycja inwestycja,PosiadajacyPieniadze pp) {

    }

}
