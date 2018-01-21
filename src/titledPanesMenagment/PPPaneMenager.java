package titledPanesMenagment;

import controllers.Listable;
import gield.Inwestycja;
import javafx.scene.control.*;
import main.Main;
import posiadajacyPieniadze.FunduszInwestycyjny;
import posiadajacyPieniadze.Inwestor;
import posiadajacyPieniadze.PosiadajacyPieniadze;

public class PPPaneMenager extends Upper{

    private TextField imieTextField,nazwiskoTextField,nazwaTextField,
        peselTextField,kapitalTextField,typTextField;
    private ChoiceBox<String> typChoiceBox;
    private ListView<String> inwestycjeListView;

    public PPPaneMenager(ListView<Listable> lista, Accordion accordion, TextField imieTextField, TextField
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

            }
        }
        wczytajListe();

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
        getLista().getItems().clear();
        synchronized (Main.getMonitor()) {
            for (String r : Main.getContainer().getHashMapInwestorow().keySet()) {
                getLista().getItems().add(Main.getContainer().getPP(r));
            }
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
                        Thread thread = new Thread(inwestor);
                        thread.setDaemon(true);
                        thread.start();
                    }
                    break;
                }
                case "Fundusz Inwestycyjny": {
                    synchronized (Main.getMonitor()) {
                        Main.getContainer().addPosiadajacyPieniadze(new FunduszInwestycyjny());
                    }
                    break;
                }
            }
            wczytajListe();
        }
    }

    @Override
    public void zapiszPola() {
        if (!getLista().getSelectionModel().isEmpty()) {
            synchronized (Main.getMonitor()) {
                PosiadajacyPieniadze pp = (PosiadajacyPieniadze) getLista().getSelectionModel().getSelectedItem();
                pp.setNazwisko(nazwiskoTextField.getText());
                pp.setKapital(Double.valueOf(kapitalTextField.getText()));
                pp.setImie(imieTextField.getText());
                if(pp instanceof Inwestor){
                    ((Inwestor) pp).setPesel(Integer.valueOf(peselTextField.getText()));
                }
                if (pp instanceof FunduszInwestycyjny){
                    ((FunduszInwestycyjny) pp).setNazwa(nazwaTextField.getText());
                }
            }
        }
        clear();
        wczytajListe();
    }


}

