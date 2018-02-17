package titledPanesManagment;

import controllers.Listable;
import gieldaPapierowWartosciowych.GieldaPapierowWartosciowych;
import gieldaPapierowWartosciowych.Indeks;
import gieldaPapierowWartosciowych.Spolka;
import javafx.scene.control.*;
import main.Main;
import posiadajacyPieniadze.PosiadajacyPieniadze;

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
        //TODO safe delete
        if(!getLista().getSelectionModel().isEmpty()){
            synchronized (Main.getMonitor()) {
                double cena=0;
                Spolka spolka = (Spolka) getLista().getSelectionModel().getSelectedItem();

                if(tg.getSelectedToggle().equals(nowaCenaRadioButton)){
                    cena = Double.parseDouble(nowaCena.getText());
                }
                else if(tg.getSelectedToggle().equals(aktualnaCenaRadioButton)){
                    cena = spolka.getAktualnyKurs();
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
        synchronized (Main.getMonitor()){
            Main.getContainer().addNewWaluta();
        }
    }

    @Override
    public void zapiszPola() {

    }
}
