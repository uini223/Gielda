package rynekwalut;

import gield.Adres;
import gield.Inwestycja;
import gieldaPapierowWartosciowych.Indeks;
import gield.Rynek;
import posiadajacyPieniadze.PosiadajacyPieniadze;

import java.util.List;

public class RynekWalut extends Rynek {
    private List<Waluta> listaWalut;
    public RynekWalut() {
        super();
    }

    @Override
    public void kupno(PosiadajacyPieniadze pp) {

    }

    @Override
    public void sprzedaz(Inwestycja inwestycja, PosiadajacyPieniadze pp) {

    }

}
