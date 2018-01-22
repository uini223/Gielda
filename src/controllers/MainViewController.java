package controllers;

import com.sun.javafx.robot.FXRobot;
import com.sun.javafx.robot.impl.FXRobotHelper;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import gield.Inwestycja;
import gield.Rynek;
import gieldaPapierowWartosciowych.Akcje;
import gieldaPapierowWartosciowych.GieldaPapierowWartosciowych;
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
        aktywaChoiceBox.getItems().addAll("Akcje Spolek","Indeksy","Waluty","Surowce");
        synchronized (Main.getMonitor()){
            rynekChoiceBox.getItems().addAll(Main.getContainer().getHashMapRynkow().values());
        }
        rynekChoiceBox.getSelectionModel().select(0);
        aktywaChoiceBox.setOnAction(Event-> onAktywaChoiceBoxAction());
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
    private void onAktywaChoiceBoxAction(){
        Rynek rynek;
        listaAkcji.getItems().clear();
        switch (aktywaChoiceBox.getSelectionModel().getSelectedItem()){
            case "Akcje Spolek":{
                synchronized (Main.getMonitor()){
                    rynek = rynekChoiceBox.getValue();
                    if(rynek instanceof GieldaPapierowWartosciowych){
                        Collection<Spolka> col = ((GieldaPapierowWartosciowych) rynek).getHashMapSpolek().values();
                        listaAkcji.getItems().addAll(col);
                    }
                }
            }
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

    private void onListaAkcjiChoice() {
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy ss");
        ObservableList<XYChart.Data<String,Number>> dane = FXCollections.observableArrayList();
        synchronized (Main.getMonitor()){
            Spolka spolka = (Spolka) listaAkcji.getSelectionModel().getSelectedItem();
            for (Date i :
                    spolka.getAkcjaSpolki().getWartosciAkcji().keySet()){

                dane.add(new XYChart.Data<>(df.format(i),spolka.getAkcjaSpolki().getWartosciAkcji().get(i)));
                //System.out.println("1");
            }
            XYChart.Series<String,Number> seria = new XYChart.Series<>();
            seria.setData(dane);
            wykresWartosci.getData().clear();
            wykresWartosci.getData().add(seria);
        }
    }
}
