package controllers;

import gield.Inwestycja;
import gield.Rynek;
import gieldaPapierowWartosciowych.GieldaPapierowWartosciowych;
import gieldaPapierowWartosciowych.Indeks;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import main.Main;
import posiadajacyPieniadze.FunduszInwestycyjny;
import posiadajacyPieniadze.Inwestor;
import posiadajacyPieniadze.PosiadajacyPieniadze;
import titledPanesMenagment.*;

import java.net.URL;
import java.util.ResourceBundle;

public class PanelKontrolnyViewController implements Initializable, Controllable {
    private Stage myStage;
    private ObservableList<TextField> textFields;
    private RynkiPaneMenager rynkiPaneMenager;
    private PPPaneMenager ppPaneMenager;
    private IndeksyPaneMenager indeksyPaneMenager;
    private SpolkiPaneMenager spolkiPaneMenager;
    private Upper menager;
    @FXML
    private BorderPane layout;
    @FXML
    ResourceBundle resources;
    @FXML
    private ChoiceBox<String> ppInwestycjeChoiceBox,rynkiTypChoiceBox,ppTypChoiceBox,
    indeksyTypChoiceBox;
    @FXML
    private Accordion accordion;
    @FXML
    private ListView<Listable> lista;
    @FXML
    private ListView<String> indeksySpolkiListView;
    @FXML
    private TextField rynkiNazwaTextField,rynkiKrajTextField,rynkiMiastoTextField,rynkiUlicaTextField,
            rynkiTypRynkuTextField,rynkiMarzaTextField,rynkiWalutaTextField;
    @FXML
    private TextField ppImieTextField,ppNazwiskoTextField,ppNazwaTextField,ppPeselTextField,ppKapitalTextField,
            ppTypTextField;
    @FXML
    private TextField indeksyNazwaTextField,indeksyGieldaTextField;

    public PanelKontrolnyViewController(){

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        rynkiPaneMenager = new RynkiPaneMenager(lista,accordion,rynkiNazwaTextField,rynkiKrajTextField,
                rynkiMiastoTextField, rynkiUlicaTextField,rynkiTypRynkuTextField,rynkiMarzaTextField,
                rynkiWalutaTextField, rynkiTypChoiceBox);

        ppPaneMenager = new PPPaneMenager(lista,accordion,ppImieTextField,ppNazwiskoTextField,ppNazwaTextField,
                ppPeselTextField,ppKapitalTextField,ppTypTextField,ppTypChoiceBox);

        indeksyPaneMenager = new IndeksyPaneMenager(lista, accordion, indeksyNazwaTextField,indeksyGieldaTextField
                ,indeksyTypChoiceBox,indeksySpolkiListView);

        spolkiPaneMenager = new SpolkiPaneMenager(lista,accordion);

        Inwestor inwestor;
        for(int i=0;i<10;i++){
            inwestor = new Inwestor();
            if(i<3) Main.getContainer().addRynek(new GieldaPapierowWartosciowych());

            Main.getContainer().addPosiadajacyPieniadze(inwestor);
            new Thread(inwestor).start();

        }

        lista.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null){
                menager.onSelectedItem();
            }
        });

        accordion.expandedPaneProperty().addListener(
                (ObservableValue<? extends TitledPane> ov, TitledPane old_val,
                 TitledPane new_val) -> {
                    if(new_val!=null){
                        switch(new_val.getText()){
                            case "Rynki":{
                                menager = rynkiPaneMenager;
                                break;
                            }
                            case "Posiadajacy Pieniadze":{
                                menager = ppPaneMenager;
                                break;
                            }
                            case "Indeksy":{
                                menager = indeksyPaneMenager;
                                break;
                            }
                            case "Spolki":{
                                menager = spolkiPaneMenager;
                                break;
                            }
                            case "Waluty":{

                                break;
                            }
                            case "Surowce":{

                                break;
                            }
                        }
                    }
                    menager.onExtendedPropertyChange(old_val,new_val);
                });

    }

    @Override
    public void setStage(Stage stage) {
        myStage = stage;
    }

    @Override
    public Stage getStage() {
        return myStage;
    }

    @FXML
    private void zapiszButtonAction(){
        menager.zapiszPola();
    }

    @FXML
    private void usunButtonAction(){
        menager.usun();
    }

    @FXML
    private void dodajNowyButtonAction(){
        menager.dodajNowy();
    }

    @FXML
    private void dodajSpolkeAction(){
        indeksyPaneMenager.dodajSpolkeDoIndeksu();
    }


}