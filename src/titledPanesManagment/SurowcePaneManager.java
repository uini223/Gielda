package titledPanesManagment;

import controllers.Listable;
import javafx.scene.control.Accordion;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import main.Main;
import posiadajacyPieniadze.PosiadajacyPieniadze;
import rynekSurowcow.RynekSurowcow;
import rynekSurowcow.Surowiec;

public class SurowcePaneManager extends ManagerAbstract {

    private TextField nazwaTextField,poczatkowyKursTextField,obecnyKursTextField,jednostkaTextField,
    gieldaTextField;

    private ListView<PosiadajacyPieniadze> listaInwestorow;

    public SurowcePaneManager(ListView<Listable> lista, Accordion accordion, TextField nazwaTextField,
                              TextField poczatkowyKursTextField, TextField obecnyKursTextField,
                              TextField jednostkaTextField, TextField gieldaTextField, ListView<PosiadajacyPieniadze>
                                      listaInwestorow) {
        super(lista, accordion);
        this.nazwaTextField = nazwaTextField;
        this.poczatkowyKursTextField = poczatkowyKursTextField;
        this.obecnyKursTextField = obecnyKursTextField;
        this.jednostkaTextField = jednostkaTextField;
        this.gieldaTextField = gieldaTextField;
        this.listaInwestorow = listaInwestorow;
        setName("Surowce");
    }

    @Override
    public void usun() {
        if(!getLista().getSelectionModel().isEmpty()){
            synchronized (Main.getMonitor()){
                Surowiec surowiec = (Surowiec) getLista().getSelectionModel().getSelectedItem();
                Main.getContainer().getHashMapSurowcow().remove(surowiec.getName());
                for (PosiadajacyPieniadze pp :
                        surowiec.getSetInwestorow()) {
                    pp.setKapital(pp.getKapital()+surowiec.getAktualnaWartosc()*
                            (int)pp.getHashMapInwestycji().get(surowiec));
                    pp.getHashMapInwestycji().remove(surowiec);
                }
                if(surowiec.getRynek() instanceof RynekSurowcow){
                    ((RynekSurowcow) surowiec.getRynek()).getHashMapSurowcow().remove(surowiec.getName());
                    if(((RynekSurowcow) surowiec.getRynek()).getHashMapSurowcow().size()==0){
                        Main.getContainer().getHashMapRynkow().remove(surowiec.getRynek().getNazwa());
                    }
                }
                getLista().getItems().remove(surowiec);
            }
        }
    }

    @Override
    public void clear() {
        nazwaTextField.clear();
        poczatkowyKursTextField.clear();
        obecnyKursTextField.clear();
        jednostkaTextField.clear();
        gieldaTextField.clear();
        listaInwestorow.getItems().clear();
    }

    @Override
    public void onSelectedItem() {
        listaInwestorow.getItems().clear();
        synchronized (Main.getMonitor()) {
            Surowiec surowiec = (Surowiec) getLista().getSelectionModel().getSelectedItem();
            nazwaTextField.setText(surowiec.getName());
            poczatkowyKursTextField.setText(String.valueOf(surowiec.getPoczatkowaWartosc()));
            obecnyKursTextField.setText(String.valueOf(surowiec.getAktualnaWartosc()));
            jednostkaTextField.setText(surowiec.getJednostkaHandlowa());
            if (surowiec.getRynek() != null) {
                gieldaTextField.setText(surowiec.getRynek().getNazwa());
            } else {
                gieldaTextField.setText("<none>");
            }
            listaInwestorow.getItems().addAll(surowiec.getSetInwestorow());
        }
    }

    @Override
    public void wczytajListe() {
        getLista().getItems().clear();
        synchronized (Main.getMonitor()){
            getLista().getItems().addAll(Main.getContainer().getHashMapSurowcow().values());
        }
    }

    @Override
    public void dodajNowy() {
        synchronized (Main.getMonitor()){
            getLista().getItems().add(Main.getContainer().addNewSurowiec());
        }
    }

    @Override
    public void refresh() {
        if(!getLista().getSelectionModel().isEmpty()){
            Surowiec surowiec = (Surowiec) getLista().getSelectionModel().getSelectedItem();
            obecnyKursTextField.setText(String.valueOf(surowiec.getAktualnaWartosc()));
            int i=-1;
            if(!listaInwestorow.getSelectionModel().isEmpty()){
                i=listaInwestorow.getSelectionModel().getSelectedIndex();
            }
            listaInwestorow.getItems().clear();
            listaInwestorow.getItems().addAll(surowiec.getSetInwestorow());
            if(i>=0){
                listaInwestorow.getSelectionModel().select(i);
            }
        }
    }

}
