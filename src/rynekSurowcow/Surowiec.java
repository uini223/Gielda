package rynekSurowcow;

import gield.Inwestycja;
import gield.Rynek;
import rynekwalut.Waluta;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Surowiec extends Inwestycja{
    private String jednostkaHandlowa;
    private Waluta waluta;
    private double aktualnaWartosc,najmniejszaWartosc,najwiekszaWartosc;

    public Surowiec() {
        super( "s",0);
        String[] surowce = {"Jabłka","Gruszki","Pomidory","Olej Silnikowy","Diament","Zboże","Opony","Ropa",""};
        Set<String> jednostkiHandlowe = new HashSet<>();
        jednostkiHandlowe.add("Kg");
        jednostkiHandlowe.add("Uncja");
        jednostkiHandlowe.add("Metry");
        jednostkiHandlowe.add("Barylki");
        jednostkiHandlowe.add("Litry");
        jednostkiHandlowe.add("Palety");
        jednostkiHandlowe.add("Sztuki");
        jednostkiHandlowe.add("MetryKwadratowe");
        jednostkiHandlowe.add("Paczki");
    }
}
