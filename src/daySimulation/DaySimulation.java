package daySimulation;

import main.Main;

public class DaySimulation implements Runnable{

    public DaySimulation() {
    }

    @Override
    public void run() {
        while (true){
            synchronized (Main.getMonitor()) {
                Main.getContainer().setSession(true);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Main.getContainer().setSession(false);

            }

        }
    }
}
