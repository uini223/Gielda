package titledPanesManagment;

import controllers.Listable;
import javafx.scene.control.Accordion;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;

public abstract
class ManagerAbstract {
    private ListView<Listable> lista;

    private Accordion accordion;

    private String name;

    public ManagerAbstract(ListView<Listable> lista, Accordion accordion) {

        this.lista = lista;
        this.accordion = accordion;
    }


    public void onExtendedPropertyChange(TitledPane old_val, TitledPane new_val) {
        if(new_val!=null) {
            if (getAccordion().getExpandedPane().getText().equals(getName()))
                if (getAccordion().getExpandedPane().getText().equals(getName())) {
                    wczytajListe();
                }
        }
        else {
            getLista().getItems().clear();
            clear();
        }
    }

    public abstract void usun();

    public abstract void clear();

    public abstract void onSelectedItem();

    public abstract void wczytajListe();

    public abstract void dodajNowy();

    public abstract void zapiszPola();

    public ListView<Listable> getLista() {
        return lista;
    }

    public void setLista(ListView<Listable> lista) {
        this.lista = lista;
    }

    public Accordion getAccordion() {
        return accordion;
    }

    public void setAccordion(Accordion accordion) {
        this.accordion = accordion;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
