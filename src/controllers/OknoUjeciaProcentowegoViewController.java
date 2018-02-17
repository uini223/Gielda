package controllers;

import gield.Inwestycja;
import gieldaPapierowWartosciowych.Spolka;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import main.Main;



import java.net.URL;
import java.util.*;

public class OknoUjeciaProcentowegoViewController implements Initializable, Controllable {
    @FXML
    private Button dodajButton,pokazButton,usunButton;
    @FXML
    private ListView<Inwestycja> allListView,usedListView;
    @FXML
    private LineChart<String,Number> wykresWartosci;
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

    @Override
    public void setStage(Stage stage) {

    }

    @Override
    public Stage getStage() {
        return null;
    }
    @FXML
    private void pokazWykres(){
        wykresWartosci.getData().clear();
        ArrayList<String> listaDat = new ArrayList<>();
        ArrayList<String> pom = new ArrayList<>();
        int notowania;
        boolean var;
        synchronized (Main.getMonitor()) {
            for (Inwestycja i :
                    usedListView.getItems()) {
                System.out.println(i.getNazwa());
                System.out.println("-------");
                var = false;
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
                        value = (wartosc - valPom) / 100;
                        lista.add(new XYChart.Data<>(s, value));
                    }
                    valPom = (double) i.getWartosciAkcji().get(s);
                    lp++;
                }

                seria.setData(lista);
                seria.setName(i.getNazwa());
                wykresWartosci.getData().add(seria);
                listaDat.clear();
                System.out.println("-------");
            }
        }
    }
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
    @FXML
    private void usunSerie(){
        if(!usedListView.getSelectionModel().isEmpty()){
            int i = usedListView.getSelectionModel().getSelectedIndex();
            wykresWartosci.getData().remove(i);
            usedListView.getItems().remove(i);
        }
    }
}
