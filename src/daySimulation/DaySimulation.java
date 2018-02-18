package daySimulation;

import controllers.MainViewController;
import controllers.OknoUjeciaProcentowegoViewController;
import javafx.application.Platform;
import main.Main;
import posiadajacyPieniadze.FunduszInwestycyjny;
import posiadajacyPieniadze.Inwestor;
import posiadajacyPieniadze.PosiadajacyPieniadze;

/**
 * symuluje dzien, jest osobnym watkiem
 */
public class DaySimulation implements Runnable{

    private volatile MainViewController mvc;
    private volatile Refresher refresher;

    public DaySimulation() {
        refresher = new Refresher();
    }

    public void setMvc(MainViewController mvc) {
        this.mvc = mvc;
    }

    /**
     *  run dla symulacji dnia (czyli symulacja dnia)
     */
    @Override
    public void run() {
        int inwestorzy=0;
        int spolki =0;
        refresher.setMvc(mvc);
        while (true){

            synchronized (Main.getMonitor()) {
                inwestorzy = Main.getContainer().getHashMapInwestorow().size();
                spolki = 2*Main.getContainer().getHashMapSpolek().size();
                if(inwestorzy>spolki){
                    usunInwestora();
                }
                else if(inwestorzy<spolki){
                    dodajInwestora();
                }
                Main.getContainer().getDate().setTime(Main.getContainer().getDate().getTime()+86400*1000);
                Main.getMonitor().notifyAll();

            }
            Platform.runLater(refresher);
            try {
                Thread.sleep(1000*30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * dodaje inwestora/fundusz inwestycyjny do swiata
     */
    private void dodajInwestora() {
        int a = (int)(Math.random()*1000);
        PosiadajacyPieniadze pp;
        Thread th;
        if(a%2==0){
            pp= new Inwestor();
        }
        else{
            pp = new FunduszInwestycyjny();
            Main.getContainer().getHashMapFunduszy().put(pp.getName(), (FunduszInwestycyjny) pp);
        }
        Main.getContainer().getHashMapInwestorow().put(pp.getName(),pp);
        th = new Thread(pp);
        th.setDaemon(true);
        th.start();
    }

    /**
     * usuwa fundusz/inwestora ze swiata
     */
    private void usunInwestora() {
        int rnd = (int)(Math.random()*10000)%Main.getContainer().getHashMapInwestorow().size();
        int n=0;
        PosiadajacyPieniadze pom=null;
        for (PosiadajacyPieniadze pp :
                Main.getContainer().getHashMapInwestorow().values()) {
            if (n == rnd) {
                pom=pp;
            }
            n++;
        }
        if(pom!=null) {
            pom.setRunning(false);
            if (pom instanceof FunduszInwestycyjny) {
                Main.getContainer().getHashMapFunduszy().remove(((FunduszInwestycyjny) pom).getNazwa());
            }
            Main.getContainer().getHashMapInwestorow().remove(pom.getName());
        }

    }

    public Refresher getRefresher() {
        return refresher;
    }

}
