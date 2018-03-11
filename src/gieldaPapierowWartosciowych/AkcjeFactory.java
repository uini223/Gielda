package gieldaPapierowWartosciowych;

public class AkcjeFactory {

    public Akcje getAkcje(Spolka spolka){
        Akcje akcje = new Akcje();
        akcje.setRynek(spolka.getRynek());
        akcje.setSpolka(spolka);
        akcje.setPoczatkowaWartosc(spolka.getKapitalWlasny()/spolka.getKapitalZakladowy());
        akcje.setAktualnaWartosc(akcje.getPoczatkowaWartosc());
        akcje.setNajmniejszaWartosc(akcje.getAktualnaWartosc());
        akcje.setNajwiekszaWartosc(akcje.getPoczatkowaWartosc());
        akcje.setName(spolka.getName());
        return akcje;
    }
}
