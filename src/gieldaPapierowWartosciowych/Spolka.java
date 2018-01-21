package gieldaPapierowWartosciowych;

import java.util.*;

public class Spolka implements Runnable{
    private String name;
    private Date dataPierwszejWyceny;
    private double kursOtwarcia, przychod, kapitalWlasny, kapitalZakladowy,  obroty, aktualnyKurs,
            maksymalnyKurs, minimalnyKurs,zysk;

    private int liczbaAkcji,wolumen;

    private Akcje akcjaSpolki;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Spolka() {
        name = losowaNazwa();
        dataPierwszejWyceny = new Date();
        kapitalZakladowy = Math.random()*1000000;
        kapitalWlasny = kapitalZakladowy+Math.random()*1000000;
        liczbaAkcji = (int)(Math.random()*10000);
        kursOtwarcia = kapitalWlasny/liczbaAkcji;
        aktualnyKurs = kursOtwarcia;
        minimalnyKurs = kursOtwarcia;
        maksymalnyKurs = kursOtwarcia;
        przychod = 0;
        zysk = 0;
        wolumen = 0;
        obroty = 0;
        akcjaSpolki = new Akcje(name,aktualnyKurs);
    }

    private String losowaNazwa() {
        String[] pierwszaCzesc = {"New", "Star", "Bez", "Odrey", "Dog", "Skoki", "music"};
        String[] drugaCzesc = {"Corp", "z o.o","sp.j", "company", "fundation", "studio", "Ehdb"};
        int zakresPierwszy = pierwszaCzesc.length;
        int zakresDrugi = drugaCzesc.length;
        return pierwszaCzesc[(int)(Math.random()*100)%zakresPierwszy]
                +drugaCzesc[(int)(Math.random()*100)%zakresDrugi]+Integer.toString(
                (int)(Math.random()*100));
    }


    public Akcje getAkcjaSpolki(){
        return akcjaSpolki;
    }

    public void generujPrzychod(){
        przychod+= Math.random()*10000;
    }

    public void generujZysk(){
        generujPrzychod();
        zysk = przychod - Math.random()*1000;
    }
    public void zmienLiczbeAkcji() {
        int znak = (int) (Math.random() * 100) % 2;
        if (znak == 0) liczbaAkcji += Math.random() * 100;
        else liczbaAkcji -= Math.random() * 100;
    }

    public void setAktualnyKurs(double aktualnyKurs) {
        if(aktualnyKurs<minimalnyKurs) minimalnyKurs = aktualnyKurs;
        if(aktualnyKurs>maksymalnyKurs) maksymalnyKurs = aktualnyKurs;
        this.aktualnyKurs = aktualnyKurs;
        akcjaSpolki.addWartoscAkcji(aktualnyKurs);
    }

    public void sprzedajAkcje(int ilosc){
        int rnd = (int)(Math.random()*100)%10+1;
        double wartosc = (aktualnyKurs*(rnd/100));
        System.out.println(wartosc);
        int plus = (int)(Math.random()*100);
        if(plus>=50){
            setAktualnyKurs(aktualnyKurs+wartosc);
        }
        else {
            setAktualnyKurs(aktualnyKurs-wartosc);
        }
        getAkcjaSpolki().addWartoscAkcji(aktualnyKurs);

    }

    public void run(){
        while(true){
            generujZysk();
            zmienLiczbeAkcji();



        }
    }
}
