package gieldaPapierowWartosciowych;

import gield.Inwestycja;
import javafx.scene.chart.NumberAxis;

import java.text.SimpleDateFormat;
import java.util.*;

public class Akcje extends Inwestycja{

    public Akcje(String nazwaSpolki,double aktualnaWartosc) {
        super(nazwaSpolki,aktualnaWartosc);
    }

    public String getNazwaSpolki() {
        return getNazwa();
    }

    @Override
    public String toString() {
        return getNazwa();
    }
}
