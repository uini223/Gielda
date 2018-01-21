package main;

import controllers.Controllable;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import static java.lang.Thread.sleep;

public class Main extends Application {
    private  Stage mainStage;
    private volatile static Container kontener=new Container();
    private javafx.event.EventHandler<WindowEvent> windowHandler;
    @Override
    public void stop(){

    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        mainStage = primaryStage;
        String name = "../views/";
        Parent root;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/PanelKontrolnyView.fxml"));
        setUserAgentStylesheet(STYLESHEET_CASPIAN);
        root = loader.load();
        primaryStage.setTitle("Giełda");
        primaryStage.setScene(new Scene(root, 900, 500));
        primaryStage.show();
        Controllable kontroler = loader.getController();
        kontroler.setStage(mainStage);

        primaryStage.setOnCloseRequest(event -> {
        });
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
