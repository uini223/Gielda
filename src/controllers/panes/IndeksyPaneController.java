package controllers.panes;

import controllers.PanelKontrolnyViewController;
import javafx.fxml.FXML;

public class IndeksyPaneController {

    private PanelKontrolnyViewController panelKontrolnyViewController;

    @FXML
    private void initialize(){

    }

    public void injectPanelKontrolnyViewController(PanelKontrolnyViewController p){
        this.panelKontrolnyViewController = p;


    }
}
