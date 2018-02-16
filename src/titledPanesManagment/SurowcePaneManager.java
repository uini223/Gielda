package titledPanesManagment;

import controllers.Listable;
import javafx.scene.control.Accordion;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import main.Main;
import posiadajacyPieniadze.PosiadajacyPieniadze;
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
        synchronized (Main.getMonitor()) {
            Surowiec surowiec = (Surowiec) getLista().getSelectionModel().getSelectedItem();
            nazwaTextField.setText(surowiec.getNazwa());
            poczatkowyKursTextField.setText("");
            obecnyKursTextField.setText(String.valueOf(surowiec.getAktualnaWartosc()));
            jednostkaTextField.setText(surowiec.getJednostkaHandlowa());
            if (surowiec.getRynek() != null) {
                gieldaTextField.setText(surowiec.getRynek().getNazwa());
            } else {
                gieldaTextField.setText("<none>");
            }
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

    }

    @Override
    public void zapiszPola() {

    }
}
