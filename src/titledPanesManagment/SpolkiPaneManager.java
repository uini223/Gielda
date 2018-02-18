package titledPanesManagment;

import controllers.Listable;
import gield.Rynek;
import gieldaPapierowWartosciowych.GieldaPapierowWartosciowych;
import gieldaPapierowWartosciowych.Indeks;
import gieldaPapierowWartosciowych.Spolka;
import javafx.scene.control.*;
import main.Main;
import posiadajacyPieniadze.PosiadajacyPieniadze;

import java.util.HashMap;
import java.util.HashSet;

public class SpolkiPaneManager extends ManagerAbstract {

    private TextField nazwa,kapitalWlasny,kapitalZakladowy,liczbaAkcji,gielda,nowaCena;

    private ListView<PosiadajacyPieniadze> listaInwestorow;

    private ListView<Indeks> listaIndeksow;

    private RadioButton nowaCenaRadioButton,aktualnaCenaRadioButton;

    private ToggleGroup tg;

    public SpolkiPaneManager(ListView<Listable> lista, Accordion accordion, TextField nazwa,
                             TextField kapitalWlasny, TextField kapitalZakladowy, TextField liczbaAkcji,
                             TextField nowaCena, ListView<Indeks> listaIndeksow, TextField gielda,
                             ListView<PosiadajacyPieniadze> listaInwestorow, RadioButton nowaCenaRadioButton,
                             RadioButton aktualnaCenaRadioButton, ToggleGroup tg) {
        super(lista, accordion);
        this.nowaCena = nowaCena;
        this.listaIndeksow = listaIndeksow;
        this.nazwa = nazwa;
        this.kapitalWlasny = kapitalWlasny;
        this.kapitalZakladowy = kapitalZakladowy;
        this.liczbaAkcji = liczbaAkcji;
        this.gielda = gielda;
        this.listaInwestorow = listaInwestorow;
        this.nowaCenaRadioButton = nowaCenaRadioButton;
        this.aktualnaCenaRadioButton = aktualnaCenaRadioButton;
        this.tg = tg;
        setName("Spolki");
    }

    @Override
    public void usun() {
        if(!getLista().getSelectionModel().isEmpty()){
            synchronized (Main.getMonitor()) {
                double cena=0;
                Spolka spolka = (Spolka) getLista().getSelectionModel().getSelectedItem();

                if(tg.getSelectedToggle().equals(nowaCenaRadioButton)){
                    cena = Double.parseDouble(nowaCena.getText());
                }
                else if(tg.getSelectedToggle().equals(aktualnaCenaRadioButton)){
                    cena = spolka.getAktualnyKurs();
                    cena =spolka.getAkcjaSpolki().getRynek().getWaluta().przelicCeneNaPLN(cena);
                }
                spolka.getAkcjaSpolki().wyprzedajWszystko(cena);
                getLista().getItems().remove(spolka);
                Main.getContainer().getHashMapSpolek().remove(spolka.getName());
                GieldaPapierowWartosciowych rynek = (GieldaPapierowWartosciowych) spolka.getAkcjaSpolki().getRynek();
                for (Indeks i :
                        spolka.getHashSetIndeksow()) {
                    if(i.getHashMapSpolek().size()==1){
                        Main.getContainer().getHashMapIndeksow().remove(i.getNazwa());
                        rynek.getHashMapIndeksow().remove(i.getNazwa());
                    }
                    i.getHashMapSpolek().remove(spolka.getName());
                }
                if(rynek.getHashMapSpolek().size()==1){
                    Main.getContainer().getHashMapRynkow().remove(rynek.getNazwa());
                }
                rynek.getHashMapSpolek().remove(spolka.getName());
                spolka.setRunning(false);
            }

        }
    }

    @Override
    public void clear() {
        nazwa.clear();
        kapitalWlasny.clear();
        listaIndeksow.getItems().clear();
        gielda.clear();
        kapitalZakladowy.clear();
        liczbaAkcji.clear();
        listaInwestorow.getItems().clear();
    }

    @Override
    public void onSelectedItem() {
        clear();
        Spolka spolka;
        spolka = (Spolka) getLista().getSelectionModel().getSelectedItem();
        aktualnaCenaRadioButton.fire();
        synchronized (Main.getMonitor()){
            listaInwestorow.getItems().addAll(spolka.getAkcjaSpolki().getSetInwestorow());
            nazwa.setText(spolka.getName());
            kapitalZakladowy.setText(String.valueOf(spolka.getKapitalZakladowy()));
            kapitalWlasny.setText(String.valueOf(spolka.getKapitalWlasny()));
            liczbaAkcji.setText(String.valueOf(spolka.getLiczbaAkcji()));
            gielda.setText(spolka.getAkcjaSpolki().getRynek().getNazwa());
            listaIndeksow.getItems().addAll(spolka.getHashSetIndeksow());
        }
    }

    @Override
    public void wczytajListe() {
        synchronized (Main.getMonitor()){
            for (Indeks i :
                    Main.getContainer().getHashMapIndeksow().values()) {
                getLista().getItems().addAll(i.getHashMapSpolek().values());

            }
        }
    }

    @Override
    public void dodajNowy() {
        /*
        synchronized (Main.getMonitor()){
            HashSet<GieldaPapierowWartosciowych> pom = new HashSet<>();
            for (Rynek r :
                    Main.getContainer().getHashMapRynkow().values()) {
                if (r instanceof GieldaPapierowWartosciowych) {
                    pom.add((GieldaPapierowWartosciowych) r);
                }
            }
            if(pom.size()>0){
                int rnd = (int) (Math.random()*1000)%pom.size();
                int n=0;
                for (GieldaPapierowWartosciowych gpw:
                        pom){
                    if(rnd==n){
                        gpw.addNewSpolka();
                    }
                    n++;
                }
                int i = 0;
                if(!getLista().getSelectionModel().isEmpty()){
                    i = getLista().getSelectionModel().getSelectedIndex();
                }
                wczytajListe();
                getLista().getSelectionModel().select(i);
            }
        }*/

    }

    @Override
    public void refresh() {
        if(!getLista().getSelectionModel().isEmpty()){
            Spolka spolka = (Spolka) getLista().getSelectionModel().getSelectedItem();
            kapitalWlasny.setText(String.valueOf(spolka.getKapitalWlasny()));
            kapitalZakladowy.setText(String.valueOf(spolka.getKapitalZakladowy()));
            liczbaAkcji.setText(String.valueOf(spolka.getLiczbaAkcji()));
            listaInwestorow.getItems().clear();
            listaInwestorow.getItems().addAll(spolka.getAkcjaSpolki().getSetInwestorow());
        }
    }

}
