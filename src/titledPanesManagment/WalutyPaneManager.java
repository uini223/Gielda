package titledPanesManagment;

import controllers.Listable;
import javafx.scene.control.Accordion;
import javafx.scene.control.ListView;

public class WalutyPaneManager extends ManagerAbstract {
    public WalutyPaneManager(ListView<Listable> lista, Accordion accordion) {
        super(lista, accordion);
    }

    @Override
    public void usun() {
        //TODO safe clear
    }

    @Override
    public void clear() {

    }

    @Override
    public void onSelectedItem() {

    }

    @Override
    public void wczytajListe() {

    }

    @Override
    public void dodajNowy() {

    }

    @Override
    public void zapiszPola() {

    }
}
