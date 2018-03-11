package titledPanesManagment;

import controllers.Listable;
import gield.Inwestycja;
import gield.Rynek;
import gieldaPapierowWartosciowych.model.GieldaPapierowWartosciowych;
import gieldaPapierowWartosciowych.model.Indeks;
import gieldaPapierowWartosciowych.model.Spolka;
import javafx.scene.control.*;
import main.Main;
import rynekSurowcow.RynekSurowcow;
import rynekSurowcow.Surowiec;
import rynekwalut.RynekWalut;
import rynekwalut.Waluta;

public class RynkiPaneManager extends ManagerAbstract {

    private TextField nazwaTextField,krajTextField,miastoTextField,ulicaTextField,
    typRynkuTextField,marzaTextField,walutaTextField;

    private Button dodajButton;

    private ChoiceBox<String> typChoiceBox;

    private ListView<Inwestycja> inwestycjaListView;

    public RynkiPaneManager(ListView<Listable> lista, Accordion accordion, TextField nazwaTextField,
                            TextField krajTextField, TextField miastoTextField, TextField ulicaTextField, TextField
                                    typRynkuTextField, TextField marzaTextField, TextField walutaTextField,
                            Button dodajButton, ChoiceBox typChoiceBox, ListView<Inwestycja> inwestycjaListView) {
        super(lista,accordion);
        this.nazwaTextField = nazwaTextField;
        this.krajTextField = krajTextField;
        this.miastoTextField = miastoTextField;
        this.ulicaTextField = ulicaTextField;
        this.typRynkuTextField = typRynkuTextField;
        this.marzaTextField = marzaTextField;
        this.walutaTextField = walutaTextField;
        this.dodajButton = dodajButton;
        this.typChoiceBox = typChoiceBox;
        this.inwestycjaListView = inwestycjaListView;
        setName("Rynki");
    }

    @Override
    public void onSelectedItem() {
        if(!getLista().getSelectionModel().isEmpty()) {
            inwestycjaListView.getItems().clear();
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
                    dodajButton.setText("Dodaj Surowiec");
                    inwestycjaListView.getItems().addAll(((RynekSurowcow) rynek).getHashMapSurowcow().values());
                }
                else if (rynek instanceof RynekWalut){
                    walutaTextField.setText(rynek.getWaluta().toString());
                    typRynkuTextField.setText("Rynek Walut");
                    dodajButton.setText("Dodaj Walute");
                    inwestycjaListView.getItems().addAll(((RynekWalut) rynek).getHashMapWalut().values());
                }
                else if (rynek instanceof  GieldaPapierowWartosciowych){
                    typRynkuTextField.setText("GPW");
                    dodajButton.setText("Dodaj Spółke");
                    for (Spolka s :
                            ((GieldaPapierowWartosciowych) rynek).getHashMapSpolek().values()) {
                        inwestycjaListView.getItems().add(s.getAkcjaSpolki());
                    }

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
        inwestycjaListView.getItems().clear();
    }
    @Override
    public void usun(){
        if(!getLista().getSelectionModel().isEmpty()){
            synchronized (Main.getMonitor()){
                Rynek rynek = (Rynek) getLista().getSelectionModel().getSelectedItem();
                Main.getContainer().getHashMapRynkow().remove(rynek.getNazwa());
                if(rynek instanceof GieldaPapierowWartosciowych){
                    for (Spolka s :
                            ((GieldaPapierowWartosciowych) rynek).getHashMapSpolek().values()) {
                        Main.getContainer().getHashMapSpolek().remove(s.getName());
                        s.getAkcjaSpolki().wyprzedajWszystko(s.getAkcjaSpolki().getAktualnaWartosc());
                        s.setRunning(false);
                        s.getHashMapIndeksow().clear();
                    }
                    for(Indeks i:
                            ((GieldaPapierowWartosciowych) rynek).getHashMapIndeksow().values()){
                        Main.getContainer().getHashMapIndeksow().remove(i.getName());
                        i.getHashMapSpolek().clear();
                    }
                }
                else if(rynek instanceof RynekWalut){
                    for(Waluta w:
                            ((RynekWalut) rynek).getHashMapWalut().values()){
                        w.setRynek(null);
                        Main.getContainer().getWalutaSet().add(w);
                    }
                }
                else if(rynek instanceof RynekSurowcow){
                    for(Surowiec s:
                            ((RynekSurowcow) rynek).getHashMapSurowcow().values()){
                        s.setRynek(null);
                        Main.getContainer().getSurowiecSet().add(s);
                    }
                }
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
    public void refresh() {

    }

    public void dodajDoRynku(){
        if(!getLista().getSelectionModel().isEmpty()){
            Rynek rynek = (Rynek) getLista().getSelectionModel().getSelectedItem();
            switch (dodajButton.getText()){
                case "Dodaj Spółke":{
                    if(rynek instanceof GieldaPapierowWartosciowych){
                        int a = (int)(Math.random()*1000)%((GieldaPapierowWartosciowych) rynek).
                                getHashMapIndeksow().values().size();
                        int n=0;
                        for (Indeks indeks :
                                ((GieldaPapierowWartosciowych) rynek).getHashMapIndeksow().values()) {
                            if (a == n) {
                                Spolka s = new Spolka();
                                indeks.dodajSpolke(s);
                                Main.getContainer().getHashMapSpolek().put(s.getName(),s);
                                s.getHashMapIndeksow().put(indeks.getName(),indeks);
                                Thread th = new Thread(s);
                                th.setDaemon(true);
                                th.start();
                            }
                            n++;
                        }
                    }
                    break;
                }
                case "Dodaj Walute":{
                    if(rynek instanceof RynekWalut){
                        ((RynekWalut) rynek).addNewWaluta();
                    }
                    break;
                }
                case "Dodaj Surowiec":{
                    if(rynek instanceof RynekSurowcow){
                        ((RynekSurowcow) rynek).addSurowiec();
                    }
                    break;
                }
            }
            onSelectedItem();
        }
    }
}
