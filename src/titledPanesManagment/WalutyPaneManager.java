package titledPanesManagment;

import controllers.Listable;
import gield.Rynek;
import javafx.scene.control.Accordion;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import main.Main;
import posiadajacyPieniadze.PosiadajacyPieniadze;
import rynekwalut.RynekWalut;
import rynekwalut.Waluta;

public class WalutyPaneManager extends ManagerAbstract {
    private TextField nazwyTextField,poczatkowyKursTextField,obecnyKursTextField,gieldaTextField,dodajPanstwoTextField;
    private ListView<PosiadajacyPieniadze> inwestorzyListView;
    private ListView<String> panstwaListView;
    public WalutyPaneManager(ListView<Listable> lista, Accordion accordion, TextField walutyNazwyTextField,
                             TextField walutyPoczatkowyKursTextField, TextField walutyObecnyKursTextField,
                             TextField walutyGieldaTextField, TextField dodajPanstwoTextField, ListView<PosiadajacyPieniadze> walutyInwestorzyListView, ListView<String> panstwaListView) {
        super(lista, accordion);
        obecnyKursTextField = walutyObecnyKursTextField;
        nazwyTextField = walutyNazwyTextField;
        poczatkowyKursTextField = walutyPoczatkowyKursTextField;
        gieldaTextField = walutyGieldaTextField;
        this.dodajPanstwoTextField = dodajPanstwoTextField;
        inwestorzyListView = walutyInwestorzyListView;
        this.panstwaListView = panstwaListView;
        setName("Waluty");
    }

    @Override
    public void usun() {
        if(!getLista().getSelectionModel().isEmpty()){
            Waluta waluta = (Waluta) getLista().getSelectionModel().getSelectedItem();
            RynekWalut rynek = (RynekWalut) waluta.getRynek();
            if(rynek!=null){
                rynek.getHashMapWalut().remove(waluta.getNazwa());
                waluta.setRynek(null);
                waluta.wyprzedajWszystko(waluta.getAktualnaWartosc());
                if(rynek.getHashMapWalut().size()==0){
                    Main.getContainer().getHashMapRynkow().remove(rynek.getNazwa());

                }
                Main.getContainer().getWalutaSet().add(waluta);
                gieldaTextField.setText("<none>");
            }
        }
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
        panstwaListView.getItems().clear();
        Waluta waluta = (Waluta) getLista().getSelectionModel().getSelectedItem();
        synchronized (Main.getMonitor()){
            nazwyTextField.setText(waluta.toString());
            obecnyKursTextField.setText(String.valueOf(waluta.getAktualnaWartosc()));
            poczatkowyKursTextField.setText(String.valueOf(waluta.getPoczatkowaWartosc()));
            if(waluta.getRynek()!=null){
                gieldaTextField.setText(waluta.getRynek().getNazwa());
            }
            else {
                gieldaTextField.setText("<none>");
            }
            inwestorzyListView.getItems().addAll(waluta.getSetInwestorow());
            panstwaListView.getItems().addAll(waluta.getListaKrajow());
        }
    }

    @Override
    public void wczytajListe() {
        getLista().getItems().clear();
        synchronized (Main.getMonitor()){
            getLista().getItems().addAll(Main.getContainer().getHashMapWalut().values());
        }
    }

    @Override
    public void dodajNowy() {
        synchronized (Main.getMonitor()){
            Main.getContainer().addNewWaluta();
        }
        wczytajListe();
    }

    @Override
    public void refresh() {
        if(!getLista().getSelectionModel().isEmpty()){
            Waluta waluta = (Waluta) getLista().getSelectionModel().getSelectedItem();
            obecnyKursTextField.setText(String.valueOf(waluta.getAktualnaWartosc()));
            int i=-1;
            if(!inwestorzyListView.getSelectionModel().isEmpty()){
                i=inwestorzyListView.getSelectionModel().getSelectedIndex();
            }
            inwestorzyListView.getItems().clear();
            inwestorzyListView.getItems().addAll(waluta.getSetInwestorow());
            if(i>=0){
                inwestorzyListView.getSelectionModel().select(i);
            }
        }
    }

    public void dodajNowePanstwo(){
        if(!dodajPanstwoTextField.getText().equals("") && !getLista().getSelectionModel().isEmpty() &&
                !panstwaListView.getItems().contains(dodajPanstwoTextField.getText())){
            synchronized (Main.getMonitor()) {
                Waluta waluta = (Waluta) getLista().getSelectionModel().getSelectedItem();
                waluta.dodajPanstwo(dodajPanstwoTextField.getText());
                panstwaListView.getItems().add(dodajPanstwoTextField.getText());
            }
        }
    }
    public void usunZeSwiata(){
        if(!getLista().getSelectionModel().isEmpty()){
            Waluta waluta = (Waluta) getLista().getSelectionModel().getSelectedItem();
            RynekWalut rynekWalut = (RynekWalut) waluta.getRynek();
            if(rynekWalut!=null){
                usun();
            }
            synchronized (Main.getMonitor()){
                Main.getContainer().getWalutaSet().remove(waluta);
                Main.getContainer().getHashMapWalut().remove(waluta.getNazwa());
            }
            getLista().getItems().remove(waluta);
        }
    }
}
