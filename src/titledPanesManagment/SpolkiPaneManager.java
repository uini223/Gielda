package titledPanesManagment;

import controllers.Listable;
import gieldaPapierowWartosciowych.Indeks;
import gieldaPapierowWartosciowych.Spolka;
import javafx.scene.control.Accordion;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import main.Main;
import posiadajacyPieniadze.PosiadajacyPieniadze;

public class SpolkiPaneManager extends ManagerAbstract {

    private TextField nazwa,kapitalWlasny,kapitalZakladowy,liczbaAkcji,indeks,gielda;

    private ListView<PosiadajacyPieniadze> listaInwestorow;

    public SpolkiPaneManager(ListView<Listable> lista, Accordion accordion, TextField nazwa,
                             TextField kapitalWlasny, TextField kapitalZakladowy, TextField liczbaAkcji,
                             TextField indeks, TextField gielda, ListView<PosiadajacyPieniadze> listaInwestorow) {
        super(lista, accordion);
        this.nazwa = nazwa;
        this.kapitalWlasny = kapitalWlasny;
        this.kapitalZakladowy = kapitalZakladowy;
        this.liczbaAkcji = liczbaAkcji;
        this.indeks = indeks;

        this.gielda = gielda;
        this.listaInwestorow = listaInwestorow;
        setName("Spolki");
    }

    @Override
    public void usun() {
        //TODO safe delete
    }

    @Override
    public void clear() {
        nazwa.clear();
        kapitalWlasny.clear();
        kapitalZakladowy.clear();
        liczbaAkcji.clear();
        indeks.clear();
        gielda.clear();
        listaInwestorow.getItems().clear();
    }

    @Override
    public void onSelectedItem() {
        listaInwestorow.getItems().clear();
        Spolka spolka;
        spolka = (Spolka) getLista().getSelectionModel().getSelectedItem();
        synchronized (Main.getMonitor()){
            listaInwestorow.getItems().addAll(spolka.getAkcjaSpolki().getSetInwestorow());
            nazwa.setText(spolka.getName());
            kapitalZakladowy.setText(String.valueOf(spolka.getKapitalZakladowy()));
            kapitalWlasny.setText(String.valueOf(spolka.getKapitalWlasny()));
            liczbaAkcji.setText(String.valueOf(spolka.getLiczbaAkcji()));
            indeks.setText(spolka.getIndeksSpolki().getNazwa());
            gielda.setText(spolka.getAkcjaSpolki().getRynek().getNazwa());
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

    }

    @Override
    public void zapiszPola() {

    }
}
