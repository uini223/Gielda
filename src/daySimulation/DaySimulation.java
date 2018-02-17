package daySimulation;

import controllers.MainViewController;
import main.Main;

public class DaySimulation implements Runnable{

    private volatile MainViewController mvc;

    public DaySimulation() {
    }

    public void setMvc(MainViewController mvc) {
        this.mvc = mvc;
    }

    @Override
    public void run() {
        int i=0;
        while (true){

            synchronized (Main.getMonitor()) {
                Main.getContainer().getDate().setTime(Main.getContainer().getDate().getTime()+86400*1000);
                Main.getMonitor().notifyAll();

            }
            mvc.updateAll();
            mvc.makeOdswiezButtonVisible();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
