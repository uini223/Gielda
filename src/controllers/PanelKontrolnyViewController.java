package controllers;

import controllers.panes.SurowcePaneController;
import gield.Inwestycja;
import gieldaPapierowWartosciowych.model.Indeks;
import gieldaPapierowWartosciowych.model.Spolka;
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

/**
 * kontroler panelu kontrolnego
 */
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
    private SurowcePaneController surowcePaneController;
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
            spolkiKapitalZakladowyTextField, spolkiLiczbaAkcjiTextField, spolkiGieldaTextField,
            spolkiWolumenTextField,spolkiPrzychodTextField,spolkiZyskTextField,spolkiObrotyTextField;
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

    /**
     * @param location
     * @param resources
     * parametry domyślne dla kontrolera,
     * inicjaluzje okienko, tworzy PaneManagery dla TitledPane'ów
     * poszczegolne PaneManagery są pseudo kontrolerami dla odpowiedniego TitledPane'u
     */
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
                spolkiZyskTextField, spolkiPrzychodTextField, spolkiIndeksListView, spolkiGieldaTextField,
                spolkiObrotyTextField, spolkiWolumenTextField, spolkiInwestorzyListView,spolkiNowaCena,
                spolkiAktualnaCena, spolkiWykupToggleGroup);

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

    /**
     * @param stage
     * setuje stage którym zarządza kontroler
     */
    @Override
    public void setStage(Stage stage) {
        myStage = stage;
        myStage.setOnCloseRequest(event -> {
            synchronized (Main.getMonitor()){
                Main.getContainer().getDaySimulation().getRefresher().setPkvc(null);
            }
        });
    }

    /**
     * @return
     * getter dla stage, którym zarządza kontroler
     */
    @Override
    public Stage getStage() {
        return myStage;
    }


    /**
     * akcja dla przycisku usun
     */
    @FXML
    private void usunButtonAction(){
        manager.usun();
    }

    /**
     * akcja dla przcisku dodaj nowy
     */
    @FXML
    private void dodajNowyButtonAction(){ manager.dodajNowy(); }

    /**
     * akcja dla przycisku dodaj spolke w indeksyPane
     */
    @FXML
    private void dodajSpolkeAction(){
        indeksyPaneManager.dodajSpolkeDoIndeksu();
    }

    /**
     * akcja dla przycisku dodaj do rynku (walute/spolke/surowiec) w rynkiPane
     */
    @FXML
    private void dodajDoRynkuButtonAction(){
        rynkiPaneManager.dodajDoRynku();
    }

    /**
     * akcja dla przycisku usun spolke z indeksu w indeksy pane
     */
    @FXML
    private void usunSpolkeZIndeksu(){
        indeksyPaneManager.usunSpolkeZIndeksu();
    }

    /**
     * akcja dla przycisku dodaj do instniejacegoo indeksu w indeksyPane
     */
    @FXML
    private void dodajIstniejacaDoIndeksu(){
        indeksyPaneManager.dodajInstiejacaDoIndeksu();
    }

    /**
     * akcja dla przycisku dodaj nowe panstwo w walutyPane
     */
    @FXML
    private void dodajNowePanstwo(){
        walutyPaneManager.dodajNowePanstwo();
    }

    /**
     * akcja dla przycisku usun ze swiata w walutyPane
     */
    @FXML
    private void usunZeSwiataButtonAction(){
        walutyPaneManager.usunZeSwiata();
    }

    /**
     * funckja do odsiwezania GUI uzywana w refresherze
     */
    public void refresh(){
        if(manager!=null) {
            manager.refresh();
        }
    }
}