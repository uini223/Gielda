package gield;

import gieldaPapierowWartosciowych.model.Akcje;

public class InwestycjaFactory  {

    public Inwestycja getInwestycja(String name ){
        name = name.toUpperCase();
        switch (name) {
            case "AKCJE": {
                return getAkcje();
            }
        }
        return null;
    }

    private Akcje getAkcje() {
        Akcje akcje = new Akcje();
        return akcje;
    }
}
