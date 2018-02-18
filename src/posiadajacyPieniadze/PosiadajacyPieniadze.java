package posiadajacyPieniadze;

import controllers.Listable;
import gield.Inwestycja;
import gieldaPapierowWartosciowych.Akcje;
import main.Main;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class PosiadajacyPieniadze implements Listable, Runnable, Serializable{

    private double kapital;

    private String imie, nazwisko;

    private HashMap<Inwestycja,Number> hashMapInwestycji;

    private String name;

    private Boolean running;

    public HashMap<Inwestycja, Number> getHashMapInwestycji() {
        return hashMapInwestycji;
    }

    public void setHashMapInwestycji(HashMap<Inwestycja, Number> hashMapInwestycji) {
        this.hashMapInwestycji = hashMapInwestycji;
    }

    public PosiadajacyPieniadze(){
        hashMapInwestycji = new HashMap<>();
        imie = generujImie();
        nazwisko = generujNazwisko();
        kapital = Math.random()*1000000+1000;
        name = imie+nazwisko+" "+Integer.toString((int)(Math.random()*10000));
        running=true;
    }

    public String getName() {
        return name;
    }

    private void updateName(){
        synchronized(Main.getMonitor()){
            Main.getContainer().getHashMapInwestorow().remove(name);
            name= imie+" "+nazwisko+" "+Integer.toString((int)(Math.random()*10000));
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
    public void kupInwestycje(){
        synchronized (Main.getMonitor()) {
            int size, i = 0, rnd;
            size = Main.getContainer().getHashMapRynkow().keySet().size();
            rnd = (int) (Math.random() * 100) % size;
            for (String s : Main.getContainer().getHashMapRynkow().keySet()
                    ) {
                if (i == rnd) {
                    Main.getContainer().getHashMapRynkow().get(s).kupno(this);
                }
                i++;
            }
        }
    }

    public  void sprzedajInwestycje(){
        if (!getHashMapInwestycji().keySet().isEmpty()) {
            int rnd = (int) (Math.random() * 100) % getHashMapInwestycji().keySet().size();
            int i = 0;
            Inwestycja inwestycja = null;
            for (Inwestycja inw : getHashMapInwestycji().keySet()
                    ) {
                if (rnd == i) {
                    inwestycja = inw;
                }
                i++;
            }
            if(inwestycja.getRynek()!=null){
                inwestycja.getRynek().sprzedaz(inwestycja,this);
            }
        }
    }

    public void generujKapital(){
        double kwota = Math.random()*100000;
        int a = (int)(Math.random()*10000)%15;
        if(a==3){
            setKapital(getKapital()+kwota);
        }
    }
    public String getImie(){
        return this.imie;
    }

    @Override
    public String toString() {
        return name;
    }

    public Boolean getRunning() {
        return running;
    }

    public void setRunning(Boolean running) {
        this.running = running;
    }
}
