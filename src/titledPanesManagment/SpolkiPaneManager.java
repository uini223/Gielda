package titledPanesManagment;

import controllers.Listable;
import gield.Rynek;
import gieldaPapierowWartosciowych.GieldaPapierowWartosciowych;
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
        if(!getLista().getSelectionModel().isEmpty()){
            synchronized (Main.getMonitor()) {
                Spolka spolka = (Spolka) getLista().getSelectionModel().getSelectedItem();
                spolka.getAkcjaSpolki().wyprzedajWszystko();
                getLista().getItems().remove(spolka);
                Main.getContainer().getHashMapSpolek().remove(spolka.getName());

                GieldaPapierowWartosciowych rynek = (GieldaPapierowWartosciowych) spolka.getAkcjaSpolki().getRynek();
                if(spolka.getIndeksSpolki().getHashMapSpolek().size()==1){
                    Indeks ind = spolka.getIndeksSpolki();
                    Main.getContainer().getHashMapIndeksow().remove(ind.getNazwa());
                    rynek.getHashMapIndeksow().remove(ind.getNazwa());
                }
                spolka.getIndeksSpolki().getHashMapSpolek().remove(spolka.getName());
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
        synchronized (Main.getMonitor()){
            Main.getContainer().addNewWaluta();
        }
    }

    @Override
    public void zapiszPola() {

    }
}
