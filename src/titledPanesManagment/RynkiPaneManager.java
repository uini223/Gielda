package titledPanesManagment;

import controllers.Listable;
import gield.Rynek;
import gieldaPapierowWartosciowych.GieldaPapierowWartosciowych;
import gieldaPapierowWartosciowych.Indeks;
import javafx.scene.control.*;
import main.Main;
import rynekSurowcow.RynekSurowcow;
import rynekwalut.RynekWalut;

public class RynkiPaneManager extends ManagerAbstract {

    private TextField nazwaTextField,krajTextField,miastoTextField,ulicaTextField,
    typRynkuTextField,marzaTextField,walutaTextField;

    private ChoiceBox<String> typChoiceBox;

    public RynkiPaneManager(ListView<Listable> lista, Accordion accordion, TextField nazwaTextField,
                            TextField krajTextField, TextField miastoTextField, TextField ulicaTextField, TextField
                            typRynkuTextField, TextField marzaTextField, TextField walutaTextField,
                            ChoiceBox typChoiceBox) {
        super(lista,accordion);
        this.nazwaTextField = nazwaTextField;
        this.krajTextField = krajTextField;
        this.miastoTextField = miastoTextField;
        this.ulicaTextField = ulicaTextField;
        this.typRynkuTextField = typRynkuTextField;
        this.marzaTextField = marzaTextField;
        this.walutaTextField = walutaTextField;
        this.typChoiceBox = typChoiceBox;
        setName("Rynki");
    }

    @Override
    public void onSelectedItem() {
        if(!getLista().getSelectionModel().isEmpty()) {
            synchronized (Main.getMonitor()) {
                Rynek rynek = (Rynek) getLista().getSelectionModel().getSelectedItem();
                krajTextField.setText(rynek.getKraj());
                miastoTextField.setText(rynek.getAdres().getMiasto());
                nazwaTextField.setText(rynek.getNazwa());
                ulicaTextField.setText(rynek.getAdres().getNazwaUlicy());
                marzaTextField.setText(String.valueOf(rynek.getMarzaOdTransakcji()));

                if(rynek instanceof RynekSurowcow){
                    walutaTextField.setText("<none>");
                    typRynkuTextField.setText("Rynek Surowcow");
                }
                else if (rynek instanceof RynekWalut){
                    walutaTextField.setText(rynek.getWaluta().toString());
                    typRynkuTextField.setText("Rynek Walut");
                }
                else if (rynek instanceof  GieldaPapierowWartosciowych){
                    typRynkuTextField.setText("GPW");
                }
            }

        }

    }


    @Override
    public void wczytajListe() {
        getLista().getItems().clear();
        synchronized (Main.getMonitor()) {
            for (String r : Main.getContainer().getHashMapRynkow().keySet()) {
                getLista().getItems().add(Main.getContainer().getRynek(r));
            }
        }
        if (typChoiceBox.getItems().isEmpty()) {
            typChoiceBox.getItems().add("Gielda Papierow Wartosciowych");
            typChoiceBox.getItems().add("Rynek Walut");
            typChoiceBox.getItems().add("Rynek Surowcow");
        }
    }

    @Override
    public void clear() {
        marzaTextField.setText("");
        nazwaTextField.setText("");
        ulicaTextField.setText("");
        miastoTextField.setText("");
        krajTextField.setText("");
        walutaTextField.setText("");
        typRynkuTextField.clear();
        typChoiceBox.getItems().clear();
    }
    @Override
    public void usun(){
        if(!getLista().getSelectionModel().isEmpty()){
            synchronized (Main.getMonitor()){
                Rynek rynek = (Rynek) getLista().getSelectionModel().getSelectedItem();
                Main.getContainer().getHashMapRynkow().remove(rynek.getNazwa());
            }
        }
        wczytajListe();

    }
    @Override
    public void dodajNowy(){
        if(!typChoiceBox.getSelectionModel().isEmpty()) {
            switch (typChoiceBox.getSelectionModel().getSelectedItem()) {
                case "Gielda Papierow Wartosciowych": {
                    synchronized (Main.getMonitor()) {
                        Main.getContainer().addRynek(new GieldaPapierowWartosciowych());
                    }
                    break;
                }
                case "Rynek Walut": {
                    synchronized (Main.getMonitor()) {
                        Main.getContainer().addRynek(new RynekWalut());
                    }
                    break;
                }
                case "Rynek Surowcow": {
                    synchronized (Main.getMonitor()) {
                        Main.getContainer().addRynek(new RynekSurowcow());
                    }
                    break;
                }

            }
            wczytajListe();

        }
    }
    @Override
    public void zapiszPola(){
        if (!getLista().getSelectionModel().isEmpty()) {
            synchronized (Main.getMonitor()) {
                Rynek gpw = (Rynek) getLista().getSelectionModel().getSelectedItem();
                gpw.setNazwa(nazwaTextField.getText());
                gpw.getAdres().setMiasto(miastoTextField.getText());
                gpw.getAdres().setNazwaUlicy(ulicaTextField.getText());
                gpw.setKraj(krajTextField.getText());
                gpw.setMarzaOdTransakcji(Double.parseDouble(marzaTextField.getText()));
            }
        }
        clear();
        wczytajListe();
    }

}
