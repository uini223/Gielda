package controllers;

import gield.Inwestycja;
import gieldaPapierowWartosciowych.model.Spolka;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import main.Main;



import java.net.URL;
import java.util.*;

/**
 *  kontroler okienka do wykresow ujecia procentowego
 */
public class OknoUjeciaProcentowegoViewController implements Initializable, Controllable {
    @FXML
    private ListView<Inwestycja> allListView,usedListView;
    @FXML
    private LineChart<String,Number> wykresWartosci;

    private Stage myStage;

    /**
     * @param location
     * @param resources
     * parametry domyslne dla kontrolera, inicjalizuje okienko ujecia procentowego
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        synchronized (Main.getMonitor()){
            allListView.getItems().addAll(Main.getContainer().getHashMapSurowcow().values());
            for (Spolka s :
                    Main.getContainer().getHashMapSpolek().values()) {
                allListView.getItems().add(s.getAkcjaSpolki());
            }
            allListView.getItems().addAll(Main.getContainer().getHashMapIndeksow().values());
            allListView.getItems().addAll(Main.getContainer().getHashMapWalut().values());
        }

    }

    /**
     * @param stage
     * setuje stage, ktorym zarządza kontroler
     */
    @Override
    public void setStage(Stage stage) {
        myStage = stage;
        myStage.setOnCloseRequest(event -> {
            synchronized (Main.getMonitor()){
                Main.getContainer().getDaySimulation().getRefresher().setOupvc(null);
            }
        });
    }

    /**
     * @return
     * zwraca obecnie zarządzany stage
     */
    @Override
    public Stage getStage() {
        return myStage;
    }

    /**
     * odpoweidzialna za wyswietlanie wykresu
     */
    @FXML
    private void pokazWykres(){
        wykresWartosci.getData().clear();
        ArrayList<String> listaDat = new ArrayList<>();
        ArrayList<String> pom = new ArrayList<>();
        int notowania;
        synchronized (Main.getMonitor()) {
            for (Inwestycja i :
                    usedListView.getItems()) {
                ObservableList<XYChart.Data<String, Number>> lista = FXCollections.observableArrayList();
                XYChart.Series<String, Number> seria = new XYChart.Series<>();
                int lp = 0;
                double value, valPom = 0, wartosc;
                listaDat.addAll(i.getWartosciAkcji().keySet());
                Collections.sort(listaDat);
                notowania = 30;
                if(listaDat.size()>notowania+1){
                    pom.clear();
                    Collections.reverse(listaDat);
                    pom.addAll(listaDat.subList(0,notowania));
                    listaDat.clear();
                    listaDat.addAll(pom);
                    Collections.sort(listaDat);
                }
                for (String s :
                        listaDat) {
                    wartosc = (double) i.getWartosciAkcji().get(s);
                    if(lp>1){
                        value = ((wartosc - valPom) / valPom )*100;
                        lista.add(new XYChart.Data<>(s, value));
                    }
                    valPom = (double) i.getWartosciAkcji().get(s);
                    lp++;
                }

                seria.setData(lista);
                seria.setName(i.getName());
                wykresWartosci.getData().add(seria);
                listaDat.clear();
            }
        }
    }

    /**
     * dodaje do listy aktywow ktore sa wyswietlane
     */
    @FXML
    private void dodajDoUsed(){
        if(!allListView.getSelectionModel().isEmpty()){
            Inwestycja inw = allListView.getSelectionModel().getSelectedItem();
            if(!usedListView.getItems().contains(inw)){
                usedListView.getItems().add(inw);
            }
        }
        pokazWykres();
    }

    /**
     * usuwa serie z wykresu
     */
    @FXML
    private void usunSerie(){
        if(!usedListView.getSelectionModel().isEmpty()){
            int i = usedListView.getSelectionModel().getSelectedIndex();
            wykresWartosci.getData().remove(i);
            usedListView.getItems().remove(i);
        }
    }

    /**
     * funkcja aktualizujaca GUI, uzywana w klasie refresher
     */
    public void odswiez(){
        pokazWykres();
    }
}
