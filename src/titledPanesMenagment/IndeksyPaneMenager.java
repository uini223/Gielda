package titledPanesMenagment;

import controllers.Listable;
import gield.Rynek;
import gieldaPapierowWartosciowych.GieldaPapierowWartosciowych;
import gieldaPapierowWartosciowych.Indeks;
import gieldaPapierowWartosciowych.Spolka;
import javafx.scene.control.Accordion;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import main.Main;

public class IndeksyPaneMenager extends Upper{
    //hello
    private TextField nazwaTextField,gieldaTextField;

    private ChoiceBox<String> typChoiceBox;

    private ListView<String> spolkiListView;

    public IndeksyPaneMenager(ListView<Listable> lista, Accordion accordion, TextField nazwaTextField,
                              TextField gieldaTextField, ChoiceBox<String>
                                      typChoiceBox, ListView<String> spolkiListView) {
        super(lista, accordion);
        this.nazwaTextField = nazwaTextField;
        this.gieldaTextField = gieldaTextField;
        this.spolkiListView = spolkiListView;
        this.typChoiceBox = typChoiceBox;
        this.spolkiListView = spolkiListView;
        setName("Indeksy");
    }

    @Override
    public void usun() {
        if(!getLista().getSelectionModel().isEmpty()){
            synchronized (Main.getContainer()){
                Indeks ind = (Indeks) getLista().getSelectionModel().getSelectedItem();
                Main.getContainer().getHashMapIndeksow().remove(ind.getNazwa());
                ind.getRodzic().getHashMapIndeksow().remove(ind.getNazwa());
            }
        }
        wczytajListe();
    }

    @Override
    public void clear() {
        nazwaTextField.clear();
        gieldaTextField.clear();
        spolkiListView.getItems().clear();
    }

    @Override
    public void onSelectedItem() {
        //wczytajListe();
        clear();
        Indeks ind;
        synchronized(Main.getContainer()){
            ind = (Indeks) getLista().getSelectionModel().getSelectedItem();
            for (String s: ind.getHashMapSpolek().keySet()
                    ) {
                spolkiListView.getItems().add(s);
            }
            nazwaTextField.setText(ind.getNazwa());
            gieldaTextField.setText(ind.getRodzic().getNazwa());
        }
    }

    @Override
    public void wczytajListe() {
        getLista().getItems().clear();
        clear();
        typChoiceBox.getItems().clear();
        synchronized(Main.getMonitor()){
            for (String s : Main.getContainer().getHashMapIndeksow().keySet()) {
                getLista().getItems().add(Main.getContainer().getIndeks(s));
            }
        }
        if(typChoiceBox.getItems().isEmpty()){
            synchronized(Main.getMonitor()){
                for (String s:Main.getContainer().getHashMapRynkow().keySet()
                        ) {
                    Rynek rynek = Main.getContainer().getRynek(s);
                    if(rynek instanceof GieldaPapierowWartosciowych){
                        typChoiceBox.getItems().add(s);
                    }
                }
            }
        }
    }

    @Override
    public void dodajNowy() {
        if(!typChoiceBox.getSelectionModel().isEmpty()){
            String s = typChoiceBox.getSelectionModel().getSelectedItem();
            GieldaPapierowWartosciowych gpw;
            synchronized(Main.getContainer()){
                gpw = (GieldaPapierowWartosciowych) Main.getContainer().getRynek(s);
                Indeks ind = new Indeks(gpw);
                gpw.addIndeks(ind);
            }
        }
        wczytajListe();
    }

    @Override
    public void zapiszPola() {
    }
    public void dodajSpolkeDoIndeksu(){
        if(!getLista().getSelectionModel().isEmpty()){
            Indeks ind = (Indeks) getLista().getSelectionModel().getSelectedItem();
            synchronized(Main.getContainer()){
                Spolka spolka = new Spolka(ind.getRynek());
                ind.dodajSpolke(spolka);
                spolkiListView.getItems().add(spolka.getName());
            }
        }
    }
}
