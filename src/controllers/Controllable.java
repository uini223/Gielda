package controllers;

import javafx.stage.Stage;

/**
 * pomocniczy interfejs dla kontrolerów
 */
public interface Controllable {
    public void setStage(Stage stage);
    public Stage getStage();
}
