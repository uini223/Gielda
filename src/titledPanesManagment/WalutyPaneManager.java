package titledPanesManagment;

import controllers.Listable;
import javafx.scene.control.Accordion;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import main.Main;
import posiadajacyPieniadze.PosiadajacyPieniadze;
import rynekwalut.Waluta;

public class WalutyPaneManager extends ManagerAbstract {
    private TextField nazwyTextField,poczatkowyKursTextField,obecnyKursTextField,gieldaTextField;
    private ListView<PosiadajacyPieniadze> inwestorzyListView;
    public WalutyPaneManager(ListView<Listable> lista, Accordion accordion, TextField walutyNazwyTextField,
                 TextField walutyPoczatkowyKursTextField, TextField walutyObecnyKursTextField,
                 TextField walutyGieldaTextField, ListView<PosiadajacyPieniadze> walutyInwestorzyListView) {
        super(lista, accordion);
        obecnyKursTextField = walutyObecnyKursTextField;
        nazwyTextField = walutyNazwyTextField;
        poczatkowyKursTextField = walutyPoczatkowyKursTextField;
        gieldaTextField = walutyGieldaTextField;
        inwestorzyListView = walutyInwestorzyListView;
        setName("Waluty");
    }

    @Override
    public void usun() {
        //TODO safe clear
    }

    @Override
    public void clear() {
        obecnyKursTextField.clear();
        nazwyTextField.clear();
        poczatkowyKursTextField.clear();
        gieldaTextField.clear();
        inwestorzyListView.getItems().clear();
    }

    @Override
    public void onSelectedItem() {
        inwestorzyListView.getItems().clear();
        Waluta waluta = (Waluta) getLista().getSelectionModel().getSelectedItem();
        synchronized (Main.getMonitor()){
            nazwyTextField.setText(waluta.toString());
            //obecnyKursTextField.setText(waluta); TODO ogarnać min i max wartosc dla inwestycji
            //poczatkowyKursTextField.setText(waluta);
            if(waluta.getRynek()!=null){
                gieldaTextField.setText(waluta.getRynek().getNazwa());
            }
            else {
                gieldaTextField.setText("<none>");
            }
            inwestorzyListView.getItems().addAll(waluta.getSetInwestorow());
        }
    }

    @Override
    public void wczytajListe() {
        synchronized (Main.getMonitor()){
            getLista().getItems().addAll(Main.getContainer().getHashMapWalut().values());
        }
    }

    @Override
    public void dodajNowy() {
        Waluta waluta;
        synchronized (Main.getMonitor()){
            Main.getContainer().addNewWaluta();
        }
    }

    @Override
    public void zapiszPola() {
        // ??
    }
}
