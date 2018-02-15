package controllers;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import posiadajacyPieniadze.PosiadajacyPieniadze;
import titledPanesManagment.*;

import java.net.URL;
import java.util.ResourceBundle;

public class PanelKontrolnyViewController implements Initializable, Controllable {
    private Stage myStage;
    private ObservableList<TextField> textFields;
    private RynkiPaneManager rynkiPaneManager;
    private PPPaneManager ppPaneManager;
    private IndeksyPaneManager indeksyPaneManager;
    private SpolkiPaneManager spolkiPaneManager;
    private ManagerAbstract manager;
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
    private ListView<String> indeksySpolkiListView,ppInwestycjeListView;
    @FXML
    private TextField rynkiNazwaTextField,rynkiKrajTextField,rynkiMiastoTextField,rynkiUlicaTextField,
            rynkiTypRynkuTextField,rynkiMarzaTextField,rynkiWalutaTextField;
    @FXML
    private TextField ppImieTextField,ppNazwiskoTextField,ppNazwaTextField,ppPeselTextField,ppKapitalTextField,
            ppTypTextField;
    @FXML
    private TextField indeksyNazwaTextField,indeksyGieldaTextField;

    @FXML
    private TextField spolkiNazwaTextField, spolkiKapitalWlasnyTextField,
            spolkiKapitalZakladowyTextField, spolkiLiczbaAkcjiTextField, spolkiIndeksTextField,
            spolkiGieldaTextField;
    @FXML
    private ListView<PosiadajacyPieniadze> spolkiInwestorzyListView,walutyInwestorzyListView;

    public PanelKontrolnyViewController(){

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        rynkiPaneManager = new RynkiPaneManager(lista,accordion,rynkiNazwaTextField,rynkiKrajTextField,
                rynkiMiastoTextField, rynkiUlicaTextField,rynkiTypRynkuTextField,rynkiMarzaTextField,
                rynkiWalutaTextField, rynkiTypChoiceBox);

        ppPaneManager = new PPPaneManager(lista,accordion,ppImieTextField,ppNazwiskoTextField,ppNazwaTextField,
                ppPeselTextField,ppKapitalTextField,ppTypTextField,ppTypChoiceBox,ppInwestycjeListView);

        indeksyPaneManager = new IndeksyPaneManager(lista, accordion, indeksyNazwaTextField,indeksyGieldaTextField
                ,indeksyTypChoiceBox,indeksySpolkiListView);

        spolkiPaneManager = new SpolkiPaneManager(lista,accordion, spolkiNazwaTextField, spolkiKapitalWlasnyTextField,
                spolkiKapitalZakladowyTextField, spolkiLiczbaAkcjiTextField, spolkiIndeksTextField,
                spolkiGieldaTextField, spolkiInwestorzyListView);

        lista.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null){
                manager.onSelectedItem();
            }
        });

        accordion.expandedPaneProperty().addListener(
                (ObservableValue<? extends TitledPane> ov, TitledPane old_val,
                 TitledPane new_val) -> {
                    if(new_val!=null){
                        switch(new_val.getText()){
                            case "Rynki":{
                                manager = rynkiPaneManager;
                                break;
                            }
                            case "Posiadajacy Pieniadze":{
                                manager = ppPaneManager;
                                break;
                            }
                            case "Indeksy":{
                                manager = indeksyPaneManager;
                                break;
                            }
                            case "Spolki":{
                                manager = spolkiPaneManager;
                                break;
                            }
                            case "Waluty":{
                                manager = walutyPaneManager;
                                break;
                            }
                            case "Surowce":{

                                break;
                            }
                        }
                    }
                    manager.onExtendedPropertyChange(old_val,new_val);
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
        manager.zapiszPola();
    }

    @FXML
    private void usunButtonAction(){
        manager.usun();
    }

    @FXML
    private void dodajNowyButtonAction(){
        manager.dodajNowy();
    }

    @FXML
    private void dodajSpolkeAction(){
        indeksyPanemanager.dodajSpolkeDoIndeksu();
    }

}