package controllers;

import com.sun.javafx.robot.impl.FXRobotHelper;
import gield.Inwestycja;
import gield.Rynek;
import gieldaPapierowWartosciowych.model.Akcje;
import gieldaPapierowWartosciowych.model.GieldaPapierowWartosciowych;
import gieldaPapierowWartosciowych.model.Indeks;
import gieldaPapierowWartosciowych.model.Spolka;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.Main;
import rynekSurowcow.RynekSurowcow;
import rynekSurowcow.Surowiec;
import rynekwalut.RynekWalut;
import rynekwalut.Waluta;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * kontroler głównego okienka
 */
public class MainViewController implements Initializable, Controllable {
    @FXML
    private ListView<Inwestycja> listaAkcji;
    @FXML
    private LineChart<String, Number> wykresWartosci;
    @FXML
    private MenuItem delete, utworzGieldeMenuItem, closeMenuItem;
    @FXML
    private Button odswiezButton;
    @FXML
    private TextField najmniejszaWartoscTextField,najwiekszaWartoscTextField,obecnaWartoscTextField,
            dataPierwszejWycenyTextField;
    @FXML
    private ChoiceBox<String> aktywaChoiceBox;
    @FXML
    private ChoiceBox<Rynek> rynekChoiceBox;


    private Stage myStage;

    public MainViewController() {
    }

    /**
     * @param location
     * @param resources
     * parametry domyślne, funkcja inicjująca główne okienko
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
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

    /**
     * @throws Exception
     * otwiera okno panelu kontrolnego (o ile takie już nie istnieje)
     */
    @FXML
    private void openPanelKontrolnyView() throws Exception {
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
            stage.setScene(new Scene(root, 900, 550));
            stage.initOwner(myStage);
            if(kontroler instanceof PanelKontrolnyViewController){
                synchronized (Main.getMonitor()){
                    Main.getContainer().getDaySimulation().getRefresher().
                            setPkvc((PanelKontrolnyViewController) kontroler);
                }
            }
            kontroler.setStage(stage);
            stage.show();
        }

    }

    /**
     *  funkcja odpowiedzialna za wyswietlanie aktywów danego rynku
     */
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
                case "Waluty":{
                    synchronized (Main.getMonitor()){
                        rynek = rynekChoiceBox.getValue();
                        if(rynek instanceof RynekWalut){
                            Collection<Waluta> col = ((RynekWalut) rynek).getHashMapWalut().values();
                            listaAkcji.getItems().addAll(col);
                        }
                    }
                }
                case "Surowce":{
                    synchronized (Main.getMonitor()){
                        rynek = rynekChoiceBox.getValue();
                        if(rynek instanceof RynekSurowcow){
                            listaAkcji.getItems().addAll(((RynekSurowcow) rynek).getHashMapSurowcow().values());
                        }
                    }
                }
            }
        }
    }

    /**
     *  funkcja ustalajaca aktywaChociceBox w zaleznosci od rynku
     */
    @FXML
    public void  onRynkiChoiceboxAction(){
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

    /**
     * @param stage
     * funkcja z interfejsu Controlable, setuje stage, którym zarzadza kontroler
     *
     */
    @Override
    public void setStage(Stage stage) {
        myStage = stage;
    }

    /**
     * @return zwraca stage kontrolera
     */
    @Override
    public Stage getStage() {
        return myStage;
    }

    /**
     *  wyswietla wykres
     */
    @FXML
    private void onListaAkcjiChoice() {
        ObservableList<XYChart.Data<String, Number>> dane = FXCollections.observableArrayList();
        if (!listaAkcji.getSelectionModel().isEmpty()) {
            synchronized (Main.getMonitor()) {
                Inwestycja inwestycja = listaAkcji.getSelectionModel().getSelectedItem();
                for (String i :
                        inwestycja.getWartosciAkcji().keySet()) {

                    dane.add(new XYChart.Data<>(i, inwestycja.getWartosciAkcji().get(i)));
                }
                XYChart.Series<String, Number> seria = new XYChart.Series<>();
                seria.setData(dane.sorted());
                seria.setName(inwestycja.getName());
                wykresWartosci.getData().clear();
                wykresWartosci.getData().add(seria);
                najmniejszaWartoscTextField.setText(String.valueOf(inwestycja.getNajmniejszaWartosc()));
                najwiekszaWartoscTextField.setText(String.valueOf(inwestycja.getNajwiekszaWartosc()));
                obecnaWartoscTextField.setText(String.valueOf(inwestycja.getAktualnaWartosc()));
                if(inwestycja instanceof Akcje){
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    Date date =((Akcje) inwestycja).getSpolka().getDataPierwszejWyceny();
                    dataPierwszejWycenyTextField.setText(df.format(date));
                }
                else {
                    dataPierwszejWycenyTextField.setText("Brak Danych");
                }
            }
        }
    }

    /**
     * pomocnicza dla Day Simulation, aktualizuje stan symulowanego świata i elementy GUI
     */
    public void updateAll() {
        //Rynek rynek = rynekChoiceBox.getValue();
        //rynekChoiceBox.getItems().clear();
        HashSet<Rynek> rynekSet = new HashSet<>();
        synchronized (Main.getMonitor()){
            onListaAkcjiChoice();
            for (Rynek rynek :
                    Main.getContainer().getHashMapRynkow().values()) {
                if(!rynekChoiceBox.getItems().contains(rynek)){
                    rynekChoiceBox.getItems().add(rynek);
                }
            }
            for (Rynek rynek :
                    rynekChoiceBox.getItems()) {
                if (!Main.getContainer().getHashMapRynkow().containsValue(rynek)) {
                    rynekSet.add(rynek);
                }
            }
            rynekChoiceBox.getItems().removeAll(rynekSet);
            int in = listaAkcji.getSelectionModel().getSelectedIndex();
            onAktywaChoiceBoxAction();
            listaAkcji.getSelectionModel().select(in);
            for (Indeks i :
                    Main.getContainer().getHashMapIndeksow().values()) {
                i.aktualizujWartosc();
            }
            for (Spolka s:
                    Main.getContainer().getHashMapSpolek().values()){
                s.getAkcjaSpolki().obliczNowyKurs();
            }
            for(Waluta w:
                    Main.getContainer().getHashMapWalut().values()){
                w.obliczNowyKurs();
            }
            for(Surowiec s:
                    Main.getContainer().getHashMapSurowcow().values()){
                s.obliczNowyKurs();
            }
        }

    }

    /**
     * @throws IOException
     * otwiera okienko do wykresów w ujęciu procentowym
     */
    @FXML
    private void openOknoUjeciaView() throws IOException {
        boolean canCreate = true;
        String title = "Okno Rysowania Ujecia Procentowego";
        for (Stage s : FXRobotHelper.getStages()
                ) {
            if (s.getTitle().equals(title)) canCreate = false;
        }
        if (canCreate) {
            FXMLLoader loader = new FXMLLoader(getClass().
                    getResource("../views/OknoUjeciaProcentowegoView.fxml"));
            Parent root;
            root = loader.load();
            Controllable kontroler = loader.getController();
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root, 900, 500));
            stage.initOwner(myStage);
            kontroler.setStage(stage);
            synchronized (Main.getMonitor()){
                Main.getContainer().getDaySimulation().getRefresher().
                        setOupvc((OknoUjeciaProcentowegoViewController) kontroler);
            }
            stage.show();
        }
    }
}
