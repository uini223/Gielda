package daySimulation;

import controllers.MainViewController;
import controllers.OknoUjeciaProcentowegoViewController;
import javafx.application.Platform;
import main.Main;

public class DaySimulation implements Runnable{

    private volatile MainViewController mvc;
    private volatile Refresher refresher;

    public DaySimulation() {
        refresher = new Refresher();
    }

    public void setMvc(MainViewController mvc) {
        this.mvc = mvc;
    }

    @Override
    public void run() {
        int i=0;
        refresher.setMvc(mvc);
        while (true){

            synchronized (Main.getMonitor()) {
                Main.getContainer().getDate().setTime(Main.getContainer().getDate().getTime()+86400*1000);
                Main.getMonitor().notifyAll();

            }
            Platform.runLater(refresher);
            try {
                Thread.sleep(1000*2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public Refresher getRefresher() {
        return refresher;
    }

    public void setRefresher(Refresher refresher) {
        this.refresher = refresher;
    }
}
