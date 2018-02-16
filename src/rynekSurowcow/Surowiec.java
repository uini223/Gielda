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

    public Surowiec(String nazwa, String jednostka, Waluta waluta) {
        super( nazwa,0);
        jednostkaHandlowa = jednostka;
        this.waluta = waluta;
        double a = Math.random()*10;
        setAktualnaWartosc(a);
        setNajmniejszaWartosc(a);
        setNajwiekszaWartosc(a);
        /*String[] surowce = {"Jabłka","Gruszki","Pomidory","Olej Silnikowy","Diament","Zboże","Opony","Ropa",""};
        Set<String> jednostkiHandlowe = new HashSet<>();
        jednostkiHandlowe.add("Kg");
        jednostkiHandlowe.add("Uncja");
        jednostkiHandlowe.add("Metry");
        jednostkiHandlowe.add("Barylki");
        jednostkiHandlowe.add("Litry");
        jednostkiHandlowe.add("Palety");
        jednostkiHandlowe.add("Sztuki");
        jednostkiHandlowe.add("MetryKwadratowe");
        jednostkiHandlowe.add("Paczki");*/
    }

    public Waluta getWaluta() {
        return waluta;
    }

    public void setWaluta(Waluta waluta) {
        this.waluta = waluta;
    }

    @Override
    public String toString() {
        return getNazwa();
    }

    public String getJednostkaHandlowa() {
        return jednostkaHandlowa;
    }

    public void setJednostkaHandlowa(String jednostkaHandlowa) {
        this.jednostkaHandlowa = jednostkaHandlowa;
    }
}
