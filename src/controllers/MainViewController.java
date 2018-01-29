package controllers;

import com.sun.javafx.robot.FXRobot;
import com.sun.javafx.robot.impl.FXRobotHelper;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import gield.Inwestycja;
import gield.Rynek;
import gieldaPapierowWartosciowych.Akcje;
import gieldaPapierowWartosciowych.GieldaPapierowWartosciowych;
import gieldaPapierowWartosciowych.Indeks;
import gieldaPapierowWartosciowych.Spolka;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import main.Main;
import posiadajacyPieniadze.Inwestor;
import posiadajacyPieniadze.PosiadajacyPieniadze;
import rynekSurowcow.RynekSurowcow;
import rynekwalut.RynekWalut;
import rynekwalut.Waluta;

import java.beans.EventHandler;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainViewController implements Initializable, Controllable {
    @FXML
    private ListView<Inwestycja> listaAkcji;
    @FXML
    private LineChart<String, Number> wykresWartosci;
    @FXML
    private MenuItem delete, utworzGieldeMenuItem, closeMenuItem;
    @FXML
    private Button guzik;
    @FXML
    private TextField najmniejszaWartosc;
    @FXML
    private ChoiceBox<String> aktywaChoiceBox;
    @FXML
    private ChoiceBox<Rynek> rynekChoiceBox;


    private Stage myStage;

    public MainViewController() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //aktywaChoiceBox.getItems().addAll("Akcje Spolek","Indeksy","Waluty","Surowce");
        synchronized (Main.getMonitor()){
            rynekChoiceBox.getItems().addAll(Main.getContainer().getHashMapRynkow().values());
        }
        rynekChoiceBox.setOnAction(Event->onRynkiChoiceboxAction());
        aktywaChoiceBox.setOnAction(Event-> onAktywaChoiceBoxAction());
        rynekChoiceBox.getSelectionModel().select(0);
        aktywaChoiceBox.getSelectionModel().select(0);
        listaAkcji.getSelectionModel().selectedItemProperty().addListener((observable,old_val,new_val) ->{
            if(new_val!=null){
                onListaAkcjiChoice();
            }
        });
    }

    @FXML
    private void deleteButtonAction() throws Exception {

    }

    @FXML
    private void anulujButtonAction() {

    }

    @FXML
    private void closeMenuItemAction() {
        myStage.close();
    }

    @FXML
    private void utworzGieldeMenuItemAction() throws Exception {
        boolean canCreate = true;
        String title = "Panel Kontrolny";
        for (Stage s : FXRobotHelper.getStages()
                ) {
            if (s.getTitle().equals(title)) canCreate = false;
        }
        if (canCreate) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/PanelKontrolnyView.fxml"));
            Parent root;
            root = loader.load();
            Controllable kontroler = loader.getController();
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root, 900, 500));
            stage.initOwner(myStage);
            kontroler.setStage(stage);
            stage.show();
        }

    }

    @FXML
    private synchronized void onAktywaChoiceBoxAction(){
        Rynek rynek;
        listaAkcji.getItems().clear();
        if(aktywaChoiceBox.getValue() != null) {
            switch (aktywaChoiceBox.getValue()) {
                case "Akcje Spolek": {
                    synchronized (Main.getMonitor()) {
                        rynek = rynekChoiceBox.getValue();
                        if (rynek instanceof GieldaPapierowWartosciowych) {
                            for (String s :
                                    ((GieldaPapierowWartosciowych) rynek).getHashMapSpolek().keySet()) {

                                listaAkcji.getItems().add(((GieldaPapierowWartosciowych) rynek).getHashMapSpolek().
                                        get(s).getAkcjaSpolki());
                            }
                        }
                    }
                    break;
                }
                case "Indeksy": {
                    synchronized (Main.getMonitor()) {
                        rynek = rynekChoiceBox.getValue();
                        if(rynek instanceof GieldaPapierowWartosciowych){
                            Collection<Indeks> col = ((GieldaPapierowWartosciowych) rynek).
                                    getHashMapIndeksow().values();
                            listaAkcji.getItems().addAll(col);
                        }
                    }
                    break;
                }
            }
        }
    }
    @FXML  synchronized void  onRynkiChoiceboxAction(){
        String s = aktywaChoiceBox.getValue();
        aktywaChoiceBox.getItems().clear();
        Rynek rynek = rynekChoiceBox.getSelectionModel().getSelectedItem();
        if(rynek instanceof GieldaPapierowWartosciowych){
            aktywaChoiceBox.getItems().addAll("Akcje Spolek","Indeksy");
        }
        if(rynek instanceof RynekWalut){
            aktywaChoiceBox.getItems().add("Waluty");
        }
        if(rynek instanceof RynekSurowcow){
            aktywaChoiceBox.getItems().add("Surowce");
        }
        if(aktywaChoiceBox.getItems().contains(s)){
            aktywaChoiceBox.setValue(s);
        }
        else{
            aktywaChoiceBox.getSelectionModel().select(0);
        }
    }
    @FXML
    private void tester() {
        //testowo
       /* if(listaAkcji.getSelectionModel().isEmpty()) {
            ObservableList<PosiadajacyPieniadze> items = listaAkcji.getItems();
            PosiadajacyPieniadze zbyszek = new Inwestor();
            //najmniejszaWartosc.setText(zbyszek.getImie());
            items.add(zbyszek);
        }*/
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
    private void pokazWykres() {
       /*SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy ss");


        ObservableList<Inwestycja> items = listaAkcji.getSelectionModel().getSelectedItems();

        ObservableList<XYChart.Data<String,Number>> dane = FXCollections.observableArrayList();

        List<XYChart.Data<String,Number>> dane = new ArrayList<>();
        int i =0;
        synchronized (Main.getMonitor()) {
            Rynek gpw;
            for (String s :
                    Main.getContainer().getHashMapRynkow().keySet()) {
                if (i == 1) {
                    gpw = Main.getContainer().getRynek(s);
                }
                i++;
            }
            if(gpw instanceof GieldaPapierowWartosciowych){
                //gpw = (GieldaPapierowWartosciowych) gpw;
                for (String s :
                        ((GieldaPapierowWartosciowych) gpw).getHashMapSpolek().keySet()) {

                }
            }
        }
        for (Inwestycja j:items
             ) {
            akcje.get(j).getWartosciAkcji().put(new Date(),Math.random());
            /*for (Date i: akcje.get(j).getWartosciAkcji().keySet()
                    ) {
                //dane.add(new XYChart.Data<>(df.format(i),akcje.get(j).getWartosciAkcji().get(i)));
                dane.add(new XYChart.Data<>(df.format(i),akcje.get(j).getWartosciAkcji().get(i)));
            } */
        //dane.addAll(test,test2);
        /*}

        XYChart.Series<String,Number> seria = new XYChart.Series<>();
        //seria.setData(dane);
        wykresWartosci.getData().clear();
        wykresWartosci.getData().add(seria);

        }
        */
    }
    @FXML
    private void onListaAkcjiChoice() {
        ObservableList<XYChart.Data<String,Number>> dane = FXCollections.observableArrayList();
        synchronized (Main.getMonitor()){
            Inwestycja inwestycja =  listaAkcji.getSelectionModel().getSelectedItem();
            for (String i :
                    inwestycja.getWartosciAkcji().keySet()){

                dane.add(new XYChart.Data<>(i,inwestycja.getWartosciAkcji().get(i)));
            }
            XYChart.Series<String,Number> seria = new XYChart.Series<>();
            seria.setData(dane.sorted());
            wykresWartosci.getData().clear();
            wykresWartosci.getData().add(seria);
        }
    }

    public void updateAll() {
        Rynek rynek = rynekChoiceBox.getValue();
        synchronized (Main.getMonitor()){
            for (String s :
                    Main.getContainer().getHashMapRynkow().keySet()) {
                if (!rynekChoiceBox.getItems().contains(Main.getContainer().getRynek(s))) {
                    rynekChoiceBox.getItems().add(Main.getContainer().getRynek(s));
                }
            }
            for (String s :
                    Main.getContainer().getHashMapIndeksow().keySet()) {
                Main.getContainer().getIndeks(s).aktualizujWartosc();
            }
        }


    }
}
