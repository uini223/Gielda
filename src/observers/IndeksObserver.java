package observers;

import gieldaPapierowWartosciowych.model.Indeks;
import gieldaPapierowWartosciowych.model.Spolka;

public class IndeksObserver extends Observer{

    private Indeks indeks;

    public IndeksObserver(Indeks indeks) {
        this.indeks = indeks;
        for (Spolka s :
                indeks.getHashMapSpolek().values()) {
            s.getAkcjaSpolki().attachObserver(this);
        }
    }

    @Override
    public synchronized void update() {
        indeks.aktualizujWartosc();
    }
}
