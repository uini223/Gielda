package titledPanesManagment;

import controllers.Listable;
import gield.Inwestycja;
import gieldaPapierowWartosciowych.Akcje;
import javafx.scene.control.*;
import main.Main;
import posiadajacyPieniadze.FunduszInwestycyjny;
import posiadajacyPieniadze.Inwestor;
import posiadajacyPieniadze.PosiadajacyPieniadze;

/**
 *  klasa dla Posiadajacych pieniadze PaneManager w panelu kontrolnym
 */
public class PPPaneManager extends ManagerAbstract {

    private TextField imieTextField,nazwiskoTextField,nazwaTextField,
        peselTextField,kapitalTextField,typTextField;
    private ChoiceBox<String> typChoiceBox;
    private ListView<String> inwestycjeListView;

    public PPPaneManager(ListView<Listable> lista, Accordion accordion, TextField imieTextField, TextField
            nazwiskoTextField, TextField nazwaTextField, TextField peselTextField, TextField kapitalTextField,
                         TextField typTextField, ChoiceBox<String> inwestycjeChoiceBox,
                         ListView<String> inwestycjeListView) {
        super(lista, accordion);
        this.imieTextField = imieTextField;
        this.nazwiskoTextField = nazwiskoTextField;
        this.nazwaTextField = nazwaTextField;
        this.peselTextField = peselTextField;
        this.kapitalTextField = kapitalTextField;
        this.typTextField = typTextField;
        this.typChoiceBox = inwestycjeChoiceBox;
        this.inwestycjeListView = inwestycjeListView;
        setName("Posiadajacy Pieniadze");
    }

    public void usun(){
        if(!getLista().getSelectionModel().isEmpty()){
            synchronized (Main.getMonitor()){
                PosiadajacyPieniadze pp = (PosiadajacyPieniadze) getLista().getSelectionModel().getSelectedItem();
                Main.getContainer().getHashMapInwestorow().remove(pp.getName());
                getLista().getItems().remove(pp);
                for (Inwestycja i :
                        pp.getHashMapInwestycji().keySet()) {
                    if(i instanceof Akcje){
                        ((Akcje) i).getSpolka().setLiczbaAkcji(((Akcje) i).getSpolka().
                                getLiczbaAkcji()+(int)pp.getHashMapInwestycji().get(i));
                    }
                }
                if(pp instanceof FunduszInwestycyjny){
                    Main.getContainer().getHashMapFunduszy().remove(((FunduszInwestycyjny) pp).getNazwa());
                }
                pp.setRunning(false);
            }
        }


    }
    @Override
    public void clear() {
        imieTextField.clear();
        nazwaTextField.clear();
        peselTextField.clear();
        kapitalTextField.clear();
        typTextField.clear();
        typChoiceBox.getItems().clear();
        inwestycjeListView.getItems().clear();
    }

    @Override
    public void onSelectedItem() {
        if (!getLista().getSelectionModel().isEmpty()) {
            synchronized (Main.getMonitor()) {
                PosiadajacyPieniadze pp = (PosiadajacyPieniadze) getLista().getSelectionModel().getSelectedItem();
                imieTextField.setText(pp.getImie());
                kapitalTextField.setText(String.valueOf(pp.getKapital()));
                nazwiskoTextField.setText(pp.getNazwisko());
                if (pp instanceof Inwestor) {
                    peselTextField.setEditable(true);
                    nazwaTextField.setText("<None>");
                    nazwaTextField.setEditable(false);
                    int x = ((Inwestor) pp).getPesel();
                    peselTextField.setText(String.valueOf(x));
                }
                if (pp instanceof FunduszInwestycyjny) {
                    nazwaTextField.setEditable(true);
                    peselTextField.setText("<none>");
                    peselTextField.setEditable(false);
                    nazwaTextField.setText(((FunduszInwestycyjny) pp).getNazwa());
                }
                typTextField.setText(pp.getClass().toString());
                inwestycjeListView.getItems().clear();
                for (Inwestycja i : pp.getHashMapInwestycji().keySet()
                        ) {
                    inwestycjeListView.getItems().add(i.toString()+" "+pp.getHashMapInwestycji().get(i));
                }

            }
        }

    }

    @Override
    public void wczytajListe() {
        //getLista().getItems().clear();
        synchronized (Main.getMonitor()) {
            getLista().getItems().addAll(Main.getContainer().getHashMapInwestorow().values());
        }
        if (typChoiceBox.getItems().isEmpty()) {
            typChoiceBox.getItems().add("Fundusz Inwestycyjny");
            typChoiceBox.getItems().add("Inwestor");
        }
    }

    @Override
    public void dodajNowy() {
        if(!typChoiceBox.getSelectionModel().isEmpty()) {
            switch (typChoiceBox.getSelectionModel().getSelectedItem()) {
                case "Inwestor": {
                    synchronized (Main.getMonitor()) {
                        Inwestor inwestor = new Inwestor();
                        Main.getContainer().addPosiadajacyPieniadze(inwestor);
                        getLista().getItems().add(inwestor);
                        Thread thread = new Thread(inwestor);
                        thread.setDaemon(true);
                        thread.start();
                    }
                    break;
                }
                case "Fundusz Inwestycyjny": {
                    synchronized (Main.getMonitor()) {
                        FunduszInwestycyjny fi = new FunduszInwestycyjny();
                        Main.getContainer().addPosiadajacyPieniadze(fi);
                        Main.getContainer().getHashMapFunduszy().put(fi.getNazwa(),fi);
                        getLista().getItems().add(fi);
                        Thread thread = new Thread(fi);
                        thread.setDaemon(true);
                        thread.start();
                    }
                    break;
                }
            }
        }
    }

    @Override
    public void refresh() {
        if(!getLista().getSelectionModel().isEmpty()){
            PosiadajacyPieniadze pp = (PosiadajacyPieniadze) getLista().getSelectionModel().getSelectedItem();
            inwestycjeListView.getItems().clear();
            for (Inwestycja i : pp.getHashMapInwestycji().keySet()
                    ) {
                inwestycjeListView.getItems().add(i.toString()+" "+pp.getHashMapInwestycji().get(i));
            }
        }
    }
}

