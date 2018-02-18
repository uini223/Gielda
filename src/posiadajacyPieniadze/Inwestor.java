package posiadajacyPieniadze;

import main.Main;

import java.util.HashMap;

/**
 *  klasa dla inwestora
 */
public class Inwestor extends PosiadajacyPieniadze {

    private HashMap<FunduszInwestycyjny,Number> fiHashMap;

    private int pesel;

    public Inwestor() {
        super();
        fiHashMap = new HashMap<>();
        pesel = (int)(Math.random()*10000000);
    }

    public int getPesel() {
        return pesel;
    }

    /**
     * metoda "kupuje" jednostki uczestnictwa losowo wybranego funduszu
     */
    public void kupJednostkiUczestictwa() {
        int size = Main.getContainer().getHashMapFunduszy().size();
        if (size > 0) {
            int a = (int) (Math.random() * 10000) % size;
            int n=0;
            FunduszInwestycyjny fi=null;
            for (FunduszInwestycyjny fundusz :
                    Main.getContainer().getHashMapFunduszy().values()) {
                if (a == n) {
                    fi = fundusz;
                }
                n++;
            }
            if(fi!=null) {
                double kwota = Math.random() * 1000;
                fi.setKapital(fi.getKapital() + kwota);
                if (fiHashMap.containsKey(fi)) {
                    kwota = kwota + (double) fiHashMap.get(fi);
                }
                fiHashMap.put(fi, kwota);
            }
        }
    }

    /**
     * metoda sprzedaje sprzedaje jednostki uczestictwa losowo wybranego funduszu
     */
    public void sprzedajJednostkiUczestnictwa(){
        if(fiHashMap.size()>0){
            int a = (int)(Math.random()*10000)%fiHashMap.size();
            int n=0;
            for (FunduszInwestycyjny fi :
                    fiHashMap.keySet()) {
                if(a==n){
                    double kwota = Math.random()*(double)fiHashMap.get(fi);
                    if(kwota==(double)fiHashMap.get(fi)){
                        fiHashMap.remove(fi);
                    }
                    else{
                        double pom = (double) fiHashMap.get(fi);
                        fiHashMap.put(fi,pom-kwota);
                    }
                    setKapital(getKapital()+kwota);
                }
            }
        }
    }

    /**
     * "życie" inwestora
     */
    @Override
    public void run() {

        while(getRunning()){
            try {
                synchronized(Main.getMonitor()) {
                    Main.getMonitor().wait();
                    int a = (int)(Math.random()*10000)%10;
                    if(a<=1){
                        kupJednostkiUczestictwa();
                    }
                    else if(a>=8){
                        sprzedajJednostkiUczestnictwa();
                    }
                    kupInwestycje();
                    sprzedajInwestycje();
                    generujKapital();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public HashMap<FunduszInwestycyjny, Number> getFiHashMap() {
        return fiHashMap;
    }

}
