package gieldaPapierowWartosciowych;

import controllers.Listable;

import java.util.ArrayList;
import java.util.List;

public class Indeks implements Listable {

    private GieldaPapierowWartosciowych rodzic;

    private List<Spolka> listaSpolek;

    private String name;

    public Indeks(GieldaPapierowWartosciowych rodzic) {
        this.rodzic = rodzic;
        listaSpolek = new ArrayList<>();
        name = "indeks" + Integer.toString((int)((Math.random())*10000));
        for (int i = 0; i < 3; i++) {
            listaSpolek.add(new Spolka());
        }
        rodzic.addIndeks(this);
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public GieldaPapierowWartosciowych getRodzic() {
        return rodzic;
    }

    public void setRodzic(GieldaPapierowWartosciowych rodzic) {
        this.rodzic = rodzic;
    }

    public List<Spolka> getListaSpolek() {
        return listaSpolek;
    }

    public void setListaSpolek(List<Spolka> listaSpolek) {
        this.listaSpolek = listaSpolek;
    }

    public int getParametrDoboruSpolek() {
        return parametrDoboruSpolek;
    }

    public void setParametrDoboruSpolek(int parametrDoboruSpolek) {
        this.parametrDoboruSpolek = parametrDoboruSpolek;
    }

    public void setName(String name) {
        this.name = name;
    }

    private int parametrDoboruSpolek;

    public void dodajSpolke(Spolka a){
        listaSpolek.add(a);
    }
    public void usunSpolke(Spolka a){

    }

}
