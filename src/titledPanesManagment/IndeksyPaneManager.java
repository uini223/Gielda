package titledPanesManagment;

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

import java.util.Collection;

public class IndeksyPaneManager extends ManagerAbstract {
    //hello
    private TextField nazwaTextField,gieldaTextField;

    private ChoiceBox<String> typChoiceBox;

    private ListView<Spolka> spolkiListView;

    private ListView<Spolka> dostepneSpolkiListVIiew;

    public IndeksyPaneManager(ListView<Listable> lista, Accordion accordion, TextField nazwaTextField,
                              TextField gieldaTextField, ChoiceBox<String>
                                      typChoiceBox, ListView<Spolka> spolkiListView, ListView<Spolka> dostepneSpolkiListVIiew) {
        super(lista, accordion);
        this.nazwaTextField = nazwaTextField;
        this.gieldaTextField = gieldaTextField;
        this.spolkiListView = spolkiListView;
        this.typChoiceBox = typChoiceBox;
        this.spolkiListView = spolkiListView;
        this.dostepneSpolkiListVIiew = dostepneSpolkiListVIiew;
        setName("Indeksy");
    }

    @Override
    public void usun() {
        if(!getLista().getSelectionModel().isEmpty()){
            synchronized (Main.getContainer()){
                Indeks ind = (Indeks) getLista().getSelectionModel().getSelectedItem();
                Main.getContainer().getHashMapIndeksow().remove(ind.getNazwa());
                ind.getRodzic().getHashMapIndeksow().remove(ind.getNazwa());
                for (Spolka s :
                        ind.getHashMapSpolek().values()) {
                    s.getHashSetIndeksow().remove(ind);
                    if(s.getHashSetIndeksow().size()==0){
                        Main.getContainer().getHashMapSpolek().remove(s.getName());
                        ind.getRodzic().getHashMapSpolek().remove(s.getName());
                        s.getAkcjaSpolki().wyprzedajWszystko(ind.getRodzic().getWaluta().
                                przelicCeneNaPLN(s.getAktualnyKurs()));
                    }
                }
                if(ind.getRodzic().getHashMapIndeksow().size()==0){
                    Main.getContainer().getHashMapRynkow().remove(ind.getRodzic().getNazwa());
                }
            }

        }
        wczytajListe();
    }

    @Override
    public void clear() {
        nazwaTextField.clear();
        gieldaTextField.clear();
        spolkiListView.getItems().clear();
        dostepneSpolkiListVIiew.getItems().clear();
    }

    @Override
    public void onSelectedItem() {
        //wczytajListe();
        clear();
        Indeks ind;
        synchronized(Main.getMonitor()){
            ind = (Indeks) getLista().getSelectionModel().getSelectedItem();
            dostepneSpolkiListVIiew.getItems().addAll(ind.getRodzic().getHashMapSpolek().values());
            dostepneSpolkiListVIiew.getItems().removeAll(ind.getHashMapSpolek().values());
            spolkiListView.getItems().addAll(ind.getHashMapSpolek().values());
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
            synchronized(Main.getMonitor()){
                gpw = (GieldaPapierowWartosciowych) Main.getContainer().getRynek(s);
                Indeks ind = new Indeks(gpw);
                gpw.addIndeks(ind);
            }
            int i = getLista().getSelectionModel().getSelectedIndex();
            wczytajListe();
            getLista().getSelectionModel().select(i);
        }
    }

    @Override
    public void refresh() {

    }

    public void dodajSpolkeDoIndeksu(){
        if(!getLista().getSelectionModel().isEmpty()){
            Indeks ind = (Indeks) getLista().getSelectionModel().getSelectedItem();
            synchronized(Main.getMonitor()){
                Spolka spolka = new Spolka(ind.getRynek());
                spolka.getHashSetIndeksow().add(ind);
                Main.getContainer().getHashMapSpolek().put(spolka.getName(),spolka);
                ind.dodajSpolke(spolka);
                spolkiListView.getItems().add(spolka);
            }
        }
    }
    public void dodajInstiejacaDoIndeksu(){
        if(!dostepneSpolkiListVIiew.getSelectionModel().isEmpty() && !getLista().getSelectionModel().isEmpty()){
            synchronized (Main.getMonitor()) {
                Spolka spolka = dostepneSpolkiListVIiew.getSelectionModel().getSelectedItem();
                Indeks indeks = (Indeks) getLista().getSelectionModel().getSelectedItem();
                indeks.dodajSpolke(spolka);
                spolka.getHashSetIndeksow().add(indeks);
                spolkiListView.getItems().add(spolka);
                dostepneSpolkiListVIiew.getItems().remove(spolka);
            }
        }
    }
    public void usunSpolkeZIndeksu(){
        if(!spolkiListView.getSelectionModel().isEmpty() && !getLista().getSelectionModel().isEmpty()){
            synchronized (Main.getMonitor()) {
                Spolka spolka = spolkiListView.getSelectionModel().getSelectedItem();
                Indeks indeks = (Indeks) getLista().getSelectionModel().getSelectedItem();
                if(spolka.getHashSetIndeksow().size()>1) {
                    if (indeks.getHashMapSpolek().values().size() < 2) {
                        indeks.getRodzic().getHashMapIndeksow().remove(indeks.getNazwa());
                        Main.getContainer().getHashMapIndeksow().remove(indeks.getNazwa());
                        getLista().getItems().remove(indeks);
                    }
                    indeks.usunSpolke(spolka);
                    spolka.getHashSetIndeksow().remove(indeks);
                    spolkiListView.getItems().remove(spolka);
                    dostepneSpolkiListVIiew.getItems().add(spolka);
                }
            }

        }
    }
}
