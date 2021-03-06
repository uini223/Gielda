package main;

import controllers.Controllable;
import gield.Rynek;
import gieldaPapierowWartosciowych.GPWBuilder;
import gieldaPapierowWartosciowych.model.GieldaPapierowWartosciowych;
import gieldaPapierowWartosciowych.model.Indeks;
import gieldaPapierowWartosciowych.model.Spolka;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;

import static java.lang.Thread.sleep;

public class Main extends Application {

    private  Stage mainStage;

    private volatile static Container kontener = new Container();

    private volatile static Object monitor = new Object();

    private volatile static Object lock = new Object();

    public static Object getMonitor() {
        return monitor;
    }

    @Override
    public void stop() throws FileNotFoundException {
        /*try {
            synchronized (Main.getMonitor()) {
                ObjectOutputStream out = new ObjectOutputStream(
                        new BufferedOutputStream(
                                new FileOutputStream("out.ser")));
                out.writeObject(kontener);
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } */

    }

    private void wczytajKontener() throws FileNotFoundException {
        try {
            ObjectInputStream in = new ObjectInputStream(
                    new BufferedInputStream(
                            new FileInputStream("out.ser")));
            kontener = (Container) in.readObject();
            Thread th;
            for (String s :kontener.getHashMapInwestorow().keySet()
                 ) {
                th = new Thread(kontener.getHashMapInwestorow().get(s));
                th.setDaemon(true);
                th.start();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void nowyStan(){
        GPWBuilder gpwBuilder = new GPWBuilder();
        gpwBuilder.setNumber(1);
        gpwBuilder.getGPW();
        System.out.println("********RYNKI**********");
        for (Rynek rynek:
                kontener.getHashMapRynkow().values()){
            System.out.println(rynek);
            if(rynek instanceof GieldaPapierowWartosciowych){
                for (Indeks indeks :
                        ((GieldaPapierowWartosciowych) rynek).getHashMapIndeksow().values()) {
                    System.out.println("****INDEKSY****");
                    System.out.println(indeks);
                    System.out.println("**SPOLKI**");
                    for (Spolka spolka :
                            indeks.getHashMapSpolek().values()) {
                        System.out.println(spolka);
                    }
                }
            }

        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
//        wczytajKontener();
        nowyStan();
        mainStage = primaryStage;
        String name = "../views/";
        Parent root;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/MainView.fxml"));
        setUserAgentStylesheet(STYLESHEET_CASPIAN);
        root = loader.load();
        primaryStage.setTitle("Giełda");
        primaryStage.setScene(new Scene(root, 900, 500));
        primaryStage.show();
        Controllable kontroler = loader.getController();
        kontroler.setStage(mainStage);
    }

    public void setView(String name) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource(name));
        mainStage.setTitle("tytul");
        mainStage.setScene(new Scene(root,300,400));
        mainStage.show();
    }

    public static synchronized Container getContainer(){
        return kontener;
    }

    public static void main(String[] args) {
        launch(args);
    }

}
