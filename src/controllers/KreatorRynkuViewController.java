package controllers;

import gieldaPapierowWartosciowych.GieldaPapierowWartosciowych;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.Main;
import rynekwalut.Waluta;


import java.net.URL;
import java.util.ResourceBundle;

public class KreatorRynkuViewController implements Initializable, Controllable {
    private String title;

    private Stage myStage;
    @FXML
    private AnchorPane ap;
    @FXML
    Button anulujButton,dodajButton, losujWartosciButton;
    @FXML
    TextField  nazwaTextField, miastoTextField, ulicaTextField, krajTextField, nrDomuTextField,
            nrMieszkaniaTextField, kodPocztowy1TextField, kodPocztowy2TextField;
    @FXML
    ChoiceBox<Waluta> walutaChoiceBox;

    public KreatorRynkuViewController() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        title = "Kreator Gieldy";

    }

    @FXML
    private void dodajButtonAction(){

        //TODO ciało funkcji dodawania
    }

    @FXML
    private void anulujButtonAction(){
        myStage.close();
    }

    @FXML
    private void losujWartosciButtonAction(){
        //TODO dopisać tablice mist,ulic,krajow....
        krajTextField.setText("Polska");
        miastoTextField.setText("Poznań");
        ulicaTextField.setText("Zachlapana");
        nazwaTextField.setText("GPP");
        nrDomuTextField.setText(String.valueOf((int)(Math.random()*100)));
        nrMieszkaniaTextField.setText(String.valueOf((int)(Math.random()*100)%20));
        kodPocztowy1TextField.setText(String.valueOf((int)(Math.random()*100)));
        kodPocztowy2TextField.setText(String.valueOf((int)(Math.random()*10000)));
        int size = walutaChoiceBox.getItems().size();
        walutaChoiceBox.setValue(walutaChoiceBox.getItems().get((int)(Math.random()*100%size)));
    }
    @Override
    public void setStage(Stage stage) {
        myStage = stage;
    }

    @Override
    public Stage getStage() {
        return myStage;
    }
}
