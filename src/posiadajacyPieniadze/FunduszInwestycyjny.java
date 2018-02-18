package posiadajacyPieniadze;


import main.Main;

/**
 * klasa dla funduszu inwestycyjnego
 */
public class FunduszInwestycyjny extends PosiadajacyPieniadze  {
    private String nazwa;

    public String getNazwa() {
        return nazwa;
    }

    public FunduszInwestycyjny() {
        super();
        nazwa = getName();
    }

    /**
     * symulacja dla funduszu, kupowanie, sprzedawanie
     */
    @Override
    public void run() {
        while (getRunning()){
            try {
                synchronized(Main.getMonitor()) {
                    Main.getMonitor().wait();
                    kupInwestycje();
                    sprzedajInwestycje();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
