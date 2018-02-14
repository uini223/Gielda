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
            mvc.updateAll();
            synchronized (Main.getMonitor()) {
                Main.getContainer().getDate().setTime(Main.getContainer().getDate().getTime()+43200*1000);
                Main.getMonitor().notifyAll();

            }
            mvc.makeOdswiezButtonVisible();
            try {
                Thread.sleep(1000*10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
