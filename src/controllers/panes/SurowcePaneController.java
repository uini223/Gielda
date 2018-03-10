package controllers.panes;

import controllers.PanelKontrolnyViewController;
import javafx.fxml.FXML;

public class SurowcePaneController  {

    PanelKontrolnyViewController panelKontrolnyViewController;

    @FXML
    private void initialize(){

    }

    public void injectPanelKontrolnyViewController(PanelKontrolnyViewController p){
        this.panelKontrolnyViewController = p;
    }

}
