package controllers;

import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class SpolkiOknoInformacyjneViewController implements Initializable, Controllable {

    private Stage myStage;

    public SpolkiOknoInformacyjneViewController() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public void setStage(Stage stage) {
        myStage = stage;
    }

    @Override
    public Stage getStage() {
        return null;
    }
}
