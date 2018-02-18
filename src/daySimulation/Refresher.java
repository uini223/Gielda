package daySimulation;

import com.sun.javafx.robot.impl.FXRobotHelper;
import controllers.MainViewController;
import controllers.OknoUjeciaProcentowegoViewController;
import controllers.PanelKontrolnyViewController;
import javafx.application.Platform;
import javafx.stage.Stage;
import main.Main;

public class Refresher implements Runnable {

    private MainViewController mvc;
    private OknoUjeciaProcentowegoViewController oupvc;
    private PanelKontrolnyViewController pkvc;

    public Refresher() {
    }

    @Override
    public void run() {
        mvc.updateAll();
        if(oupvc!=null){
            oupvc.odswiez();
        }
        if(pkvc!=null){
            pkvc.refresh();
        }
    }

    public void setMvc(MainViewController mvc) {
        this.mvc = mvc;
    }

    public OknoUjeciaProcentowegoViewController getOupvc() {
        return oupvc;
    }

    public void setOupvc(OknoUjeciaProcentowegoViewController oupvc) {
        this.oupvc = oupvc;
    }

    public PanelKontrolnyViewController getPkvc() {
        return pkvc;
    }

    public void setPkvc(PanelKontrolnyViewController pkvc) {
        this.pkvc = pkvc;
    }
}
