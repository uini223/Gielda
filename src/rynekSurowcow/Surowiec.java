package rynekSurowcow;

import gield.Inwestycja;
import rynekwalut.Waluta;

public class Surowiec extends Inwestycja{
    private String jednostkaHandlowa;
    private Waluta waluta;
    private double aktualnaWartosc,najmniejszaWartosc,najwiekszaWartosc;

    public Surowiec() {

        super("s",0);
    }
}
