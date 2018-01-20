package controllers;

import com.sun.javafx.robot.FXRobot;
import com.sun.javafx.robot.impl.FXRobotHelper;
import gieldaPapierowWartosciowych.Akcje;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import main.Main;
import posiadajacyPieniadze.Inwestor;
import posiadajacyPieniadze.PosiadajacyPieniadze;

import java.beans.EventHandler;
import java.net.URL;
import java.util.*;

public class MainViewController implements Initializable, Controllable{
    @FXML
    private ListView<PosiadajacyPieniadze> listaAkcji;
    @FXML
    private Text titleText;
    @FXML
    private LineChart<String,Number> wykresWartosci;
    @FXML
    private MenuItem delete,utworzGieldeMenuItem,closeMenuItem;
    @FXML
    private Button guzik;
    @FXML
    private URL location;
    @FXML
    private ResourceBundle resource;

    @FXML
    private TextField najmniejszaWartosc;

    private Map<String,Akcje> akcje;

    private List<Date> test;

    private List<Number> test2;

    private Stage myStage;

    public MainViewController(){
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        titleText.setText("sdad");
    }

    @FXML private void deleteButtonAction() throws Exception{

    }

    @FXML private void anulujButtonAction(){

    }

    @FXML private void closeMenuItemAction(){
        myStage.close();
    }

    @FXML private void utworzGieldeMenuItemAction() throws Exception{
        boolean canCreate = true;
        String title = "Kreator Gieldy";
        for (Stage s: FXRobotHelper.getStages()
                ) {
            if(s.getTitle().equals(title)) canCreate = false;
        }
        if(canCreate) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/KreatorRynkuView.fxml"));
            Parent root;
            root = loader.load();
            Controllable kontroler = loader.getController();
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root, 500, 200));
            stage.setResizable(false);
            stage.initOwner(myStage);
            kontroler.setStage(stage);
            stage.show();
        }

    }

    @FXML
    private void tester(){
        //testowo
        if(listaAkcji.getSelectionModel().isEmpty()) {
            ObservableList<PosiadajacyPieniadze> items = listaAkcji.getItems();
            PosiadajacyPieniadze zbyszek = new Inwestor();
            //najmniejszaWartosc.setText(zbyszek.getImie());
            items.add(zbyszek);
        }
    }

    @Override
    public void setStage(Stage stage) {
        myStage = stage;
    }

    @Override
    public Stage getStage() {
        return myStage;
    }

    //@FXML
//    private void pokazWykres(){
//        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy ss");
//
//        test.add(new Date());
//        test2.add(Math.random());
//
//        //ObservableList<String> items = listaAkcji.getSelectionModel().getSelectedItems();
//        //ObservableList<XYChart.Data<String,Number>> dane = FXCollections.observableArrayList();
//        List<XYChart.Data<String,Number>> dane = new ArrayList<>();
//        for (String j:items
//             ) {
//            akcje.get(j).getWartosciAkcji().put(new Date(),Math.random());
//            /*for (Date i: akcje.get(j).getWartosciAkcji().keySet()
//                    ) {
//                //dane.add(new XYChart.Data<>(df.format(i),akcje.get(j).getWartosciAkcji().get(i)));
//                dane.add(new XYChart.Data<>(df.format(i),akcje.get(j).getWartosciAkcji().get(i)));
//            } */
//            //dane.addAll(test,test2);
//        }
//
//        XYChart.Series<String,Number> seria = new XYChart.Series<>();
//        //seria.setData(dane);
//        wykresWartosci.getData().clear();
//        wykresWartosci.getData().add(seria); */
//    }
}
