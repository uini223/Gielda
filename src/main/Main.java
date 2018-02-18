package main;

import controllers.Controllable;
import controllers.MainViewController;
import daySimulation.DaySimulation;
import gield.Rynek;
import gieldaPapierowWartosciowych.GieldaPapierowWartosciowych;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import posiadajacyPieniadze.Inwestor;
import posiadajacyPieniadze.PosiadajacyPieniadze;
import rynekSurowcow.RynekSurowcow;
import rynekwalut.RynekWalut;

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
        try {
            synchronized (Main.getMonitor()) {
                ObjectOutputStream out = new ObjectOutputStream(
                        new BufferedOutputStream(
                                new FileOutputStream("out.ser")));
                out.writeObject(kontener);
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

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
        ThreadGroup threadGroup = new ThreadGroup("Ala");
        Rynek rynek;
        PosiadajacyPieniadze pp;
        Thread thread;
        for(int i=0;i<24;i++){
            if(i<2){
                rynek = new GieldaPapierowWartosciowych();
                kontener.addRynek(rynek);
                rynek = new RynekWalut();
                kontener.addRynek(rynek);
                rynek = new RynekSurowcow();
                kontener.addRynek(rynek);
            }
            pp = new Inwestor();
            kontener.addPosiadajacyPieniadze(pp);
            thread = new Thread(threadGroup,pp);
            thread.setDaemon(true);
            thread.start();
        }
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        wczytajKontener();
        //nowyStan();
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

        DaySimulation day = new DaySimulation();
        if(kontroler instanceof MainViewController) day.setMvc((MainViewController)kontroler);
        Thread th = new Thread(day);
        getContainer().setDaySimulation(day);
        th.setDaemon(true);
        th.start();
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
