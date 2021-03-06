package observers;

import gieldaPapierowWartosciowych.model.GieldaPapierowWartosciowych;
import gieldaPapierowWartosciowych.model.Indeks;

public class GPWObserver extends Observer{

    private GieldaPapierowWartosciowych gieldaPapierowWartosciowych;

    public GPWObserver(GieldaPapierowWartosciowych gieldaPapierowWartosciowych) {
        this.gieldaPapierowWartosciowych = gieldaPapierowWartosciowych;
        for (Indeks indeks :
                this.gieldaPapierowWartosciowych.getHashMapIndeksow().values()) {
            indeks.attachObserver(this);
        }
    }

    @Override
    public void update() {
        gieldaPapierowWartosciowych.aktualizujSpolki();
    }
}
