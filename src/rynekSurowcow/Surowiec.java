package rynekSurowcow;

import gield.Inwestycja;
import gield.Rynek;
import rynekwalut.Waluta;

public class Surowiec extends Inwestycja{
    private String jednostkaHandlowa;
    private Waluta waluta;
    private double aktualnaWartosc,najmniejszaWartosc,najwiekszaWartosc;

    public Surowiec(Rynek rynek) {
        super(rynek, "s",0);
        String[] surowce = {"Jabłka","Gruszki","Pomidory","Olej Silnikowy","Diament","Zboże","Opony","Ropa",""};

    }
}
