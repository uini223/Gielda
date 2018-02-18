package posiadajacyPieniadze;


import main.Main;

public class FunduszInwestycyjny extends PosiadajacyPieniadze  {
    private String nazwa;

    public String getNazwa() {
        return nazwa;
    }

    public FunduszInwestycyjny() {
        super();
        nazwa = getName();
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

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
