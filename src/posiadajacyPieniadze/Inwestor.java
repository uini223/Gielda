package posiadajacyPieniadze;

import gield.Inwestycja;
import main.Main;
import rynekwalut.Waluta;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Inwestor extends PosiadajacyPieniadze {


    private int pesel;

    public Inwestor() {
        super();
        pesel = (int)(Math.random()*10000000);
    }

    public int getPesel() {
        return pesel;
    }

    public void setPesel(int pesel) {
        this.pesel = pesel;
    }

    @Override
    public void kupInwestycje()  {
        System.out.println("Kupuje");
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

    @Override
    public void sprzedajInwestycje() {
        System.out.println("Sprzedaje");
        if(!getHashMapInwestycji().keySet().isEmpty()) {
            int rnd = (int) (Math.random() * 100) % getHashMapInwestycji().keySet().size();
            int i = 0;
            for (Inwestycja inw : getHashMapInwestycji().keySet()
                    ) {
                if (rnd == i) {
                    inw.getRynek().sprzedaz(inw,this);
                }
                i++;
            }
        }
    }
    public void kupJednostkiUczestictwa() {

    }
    public void sperzedajJednostkiUczestnictwa(){

    }

    @Override
    public void run() {

        for (int i=0;i<1000;i++){
            try {
                synchronized(Main.getMonitor()) {
                    Main.getMonitor().wait();
                    kupInwestycje();
                    sprzedajInwestycje();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //System.out.println(this.getName());
        }
    }
}
