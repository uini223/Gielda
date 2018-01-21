package posiadajacyPieniadze;

import gield.Inwestycja;
import main.Main;

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
    public void kupInwestycje() {
        synchronized(Main.getMonitor()){
            int size,i=0,rnd;
            size = Main.getContainer().getHashMapRynkow().keySet().size();
            rnd = (int)(Math.random()*100)%size;
            for (String s:Main.getContainer().getHashMapRynkow().keySet()
                    ) {
                if(i == rnd){
                    Main.getContainer().getHashMapRynkow().get(s).kupno(this);
                }
                i++;
            }
        }
    }

    @Override
    public void sprzedajInwestycje() {

    }
    public void kupJednostkiUczestictwa() {

    }
    public void sperzedajJednostkiUczestnictwa(){

    }

    @Override
    public void run() {

        while(true){
            kupInwestycje();
            sprzedajInwestycje();
            //System.out.println(this.getName());

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
