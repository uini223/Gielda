package daySimulation;

import com.sun.javafx.robot.impl.FXRobotHelper;
import controllers.MainViewController;
import controllers.OknoUjeciaProcentowegoViewController;
import javafx.application.Platform;
import javafx.stage.Stage;
import main.Main;

public class Refresher implements Runnable {

    private MainViewController mvc;
    private OknoUjeciaProcentowegoViewController oupvc;

    public Refresher() {
    }

    @Override
    public void run() {
        mvc.updateAll();
        if(oupvc!=null){
            oupvc.odswiez();
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
}
