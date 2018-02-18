package controllers;

import gield.Inwestycja;
import gieldaPapierowWartosciowych.Indeks;
import gieldaPapierowWartosciowych.Spolka;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.Main;
import posiadajacyPieniadze.PosiadajacyPieniadze;
import titledPanesManagment.*;

import java.net.URL;
import java.util.ResourceBundle;

public class PanelKontrolnyViewController implements Initializable, Controllable {
    private Stage myStage;
    private RynkiPaneManager rynkiPaneManager;
    private PPPaneManager ppPaneManager;
    private IndeksyPaneManager indeksyPaneManager;
    private SpolkiPaneManager spolkiPaneManager;
    private ManagerAbstract manager;
    private WalutyPaneManager walutyPaneManager;
    private SurowcePaneManager surowcePaneManager;

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
    private ListView<String> ppInwestycjeListView,walutyPanstwaListView;
    @FXML
    private TextField rynkiNazwaTextField,rynkiKrajTextField,rynkiMiastoTextField,rynkiUlicaTextField,
            rynkiTypRynkuTextField,rynkiMarzaTextField,rynkiWalutaTextField;
    @FXML
    private TextField ppImieTextField,ppNazwiskoTextField,ppNazwaTextField,ppPeselTextField,ppKapitalTextField,
            ppTypTextField;
    @FXML
    private TextField indeksyNazwaTextField,indeksyGieldaTextField;
    @FXML
    private ListView<Indeks> spolkiIndeksListView;
    @FXML
    private TextField spolkiNazwaTextField, spolkiKapitalWlasnyTextField,spolkiNowaCenaTextField,
            spolkiKapitalZakladowyTextField, spolkiLiczbaAkcjiTextField, spolkiGieldaTextField;
    @FXML
    private TextField walutyNazwaTextField,walutyPoczatkowyKursTextField,walutyObecnyKursTextField,
            walutyGieldaTextField,dodajNowePanstwoTextField;
    @FXML
    private TextField surowceNazwaTextField,surowcePoczatkowyKursTextField,surowceObecnyKursTextField,
            surowceJednostkaTextField,surowceGieldaTextField;
    @FXML
    private ListView<PosiadajacyPieniadze> spolkiInwestorzyListView,walutyInwestorzyListView,
            surowceInwestorzyListView;
    @FXML
    private Button rynkiDodajButton;
    @FXML
    private ListView<Inwestycja> rynkiInwestycjaListView;
    @FXML
    private ListView<Spolka> dostepneSpolkiListVIiew,indeksySpolkiListView;
    @FXML
    private ToggleGroup spolkiWykupToggleGroup;
    @FXML
    private RadioButton spolkiNowaCena,spolkiAktualnaCena;
    public PanelKontrolnyViewController(){

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        rynkiPaneManager = new RynkiPaneManager(lista,accordion,rynkiNazwaTextField,rynkiKrajTextField,
                rynkiMiastoTextField, rynkiUlicaTextField,rynkiTypRynkuTextField,rynkiMarzaTextField,
                rynkiWalutaTextField, rynkiDodajButton, rynkiTypChoiceBox, rynkiInwestycjaListView);

        ppPaneManager = new PPPaneManager(lista,accordion,ppImieTextField,ppNazwiskoTextField,ppNazwaTextField,
                ppPeselTextField,ppKapitalTextField,ppTypTextField,ppTypChoiceBox,ppInwestycjeListView);

        indeksyPaneManager = new IndeksyPaneManager(lista, accordion, indeksyNazwaTextField,indeksyGieldaTextField
                ,indeksyTypChoiceBox,indeksySpolkiListView, dostepneSpolkiListVIiew);

        spolkiPaneManager = new SpolkiPaneManager(lista,accordion, spolkiNazwaTextField, spolkiKapitalWlasnyTextField,
                spolkiKapitalZakladowyTextField, spolkiLiczbaAkcjiTextField, spolkiNowaCenaTextField,
                spolkiIndeksListView, spolkiGieldaTextField, spolkiInwestorzyListView,spolkiNowaCena,spolkiAktualnaCena,
                spolkiWykupToggleGroup);

        walutyPaneManager = new WalutyPaneManager(lista,accordion, walutyNazwaTextField, walutyPoczatkowyKursTextField,
                walutyObecnyKursTextField, walutyGieldaTextField, dodajNowePanstwoTextField,walutyInwestorzyListView,
                walutyPanstwaListView);

        surowcePaneManager = new SurowcePaneManager(lista,accordion,surowceNazwaTextField,
                surowcePoczatkowyKursTextField,surowceObecnyKursTextField,surowceJednostkaTextField,
                surowceGieldaTextField,surowceInwestorzyListView);

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
                                manager = surowcePaneManager;
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
        myStage.setOnCloseRequest(event -> {
            synchronized (Main.getMonitor()){
                Main.getContainer().getDaySimulation().getRefresher().setPkvc(null);
            }
        });
    }

    @Override
    public Stage getStage() {
        return myStage;
    }


    @FXML
    private void usunButtonAction(){
        manager.usun();
    }

    @FXML
    private void dodajNowyButtonAction(){ manager.dodajNowy(); }

    @FXML
    private void dodajSpolkeAction(){
        indeksyPaneManager.dodajSpolkeDoIndeksu();
    }

    @FXML
    private void dodajDoRynkuButtonAction(){
        rynkiPaneManager.dodajDoRynku();
    }
    @FXML
    private void usunSpolkeZIndeksu(){
        indeksyPaneManager.usunSpolkeZIndeksu();
    }
    @FXML
    private void dodajIstniejacaDoIndeksu(){
        indeksyPaneManager.dodajInstiejacaDoIndeksu();
    }
    @FXML
    private void dodajNowePanstwo(){
        walutyPaneManager.dodajNowePanstwo();
    }
    @FXML
    private void usunZeSwiataButtonAction(){
        walutyPaneManager.usunZeSwiata();
    }
    public void refresh(){
        if(manager!=null) {
            manager.refresh();
        }
    }
}