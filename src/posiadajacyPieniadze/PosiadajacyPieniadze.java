package posiadajacyPieniadze;

import controllers.Listable;
import gield.Inwestycja;
import main.Main;

import java.util.ArrayList;
import java.util.List;

public abstract class PosiadajacyPieniadze implements Listable, Runnable{
    private double kapital;

    private String imie, nazwisko;

    private List<Inwestycja> listaInwestycji;

    private String name;

    public PosiadajacyPieniadze(){
        listaInwestycji = new ArrayList<>();
        imie = generujImie();
        nazwisko = generujNazwisko();
        kapital = Math.random()*1000000;
        name = imie+nazwisko+" "+Integer.toString((int)(Math.random()*10000));
    }

    public String getName() {
        return name;
    }
    public void updateName(){
        synchronized(Main.getContainer()){
            Main.getContainer().getHashMapInwestorow().remove(name);
            name= imie + nazwisko+" "+Integer.toString((int)(Math.random()*10000));
            Main.getContainer().getHashMapInwestorow().put(name,this);
        }
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setImie(String imie) {
        this.imie = imie;
        updateName();
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
        updateName();
    }

    public List<Inwestycja> getListaInwestycji() {
        return listaInwestycji;
    }

    public void setListaInwestycji(List<Inwestycja> listaInwestycji) {
        this.listaInwestycji = listaInwestycji;
    }

    public double getKapital() {
        return kapital;
    }

    public void setKapital(double kapital) {
        this.kapital = kapital;
    }

    protected String generujImie(){
        String imiona[] = {"Jan", "Ramon", "Zbigniew", "Zeus", "Apollo", "Mercedes", "Katarzyna", "Adrianna", "Alicja"
        , "Dawid", "Emil", "Jacek", "Wiktor", "SomeRandomName", "Jędrzej", "Stanisław", "Daniel", "Borys", "Magda",
        "Aleksandra", "Kacper", "Julita", "Weronika", "Tekla", "Joanna", "Filip", "Piotr", "Kuba"};
        return imiona[(int)(Math.random()*100)%imiona.length];
    }
    protected String generujNazwisko(){
        String nazwiska[] = {"Politechnika", "Kurczak", "KFC", "Formula1", "Benz", "Disel", "Van Gogh", "Trabalski",
                "PlugIn","SomeRandomSurname", "Studnia", "Bończyk", "Szataniak", "Picasso", "Chopin", "Spacey",
        "Nowak", "Kowalski", "Predator", "Gossling", "Omen", "Sinus", "Cosinus", "Johanson", "Drop", "8bit"};
        return nazwiska[(int)(Math.random()*100)%nazwiska.length];
    }
    public abstract void kupInwestycje();

    public abstract void sprzedajInwestycje();

    public String getImie(){
        return this.imie;
    }

    @Override
    public String toString() {
        return name;
    }
}
