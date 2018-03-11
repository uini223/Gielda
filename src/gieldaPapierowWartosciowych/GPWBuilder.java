package gieldaPapierowWartosciowych;

import observers.GPWObserver;

public class GPWBuilder {

    private IndeksBuilder indeksBuilder = new IndeksBuilder();
    private GPWFactory gpwFactory = new GPWFactory();
    private int number = 3;

    public GieldaPapierowWartosciowych getGPW(){
        GieldaPapierowWartosciowych gpw = new GPWFactory().getGPW();
        for (int i=0;i<number;i++){
            gpw.addIndeks(indeksBuilder.getIndeks(gpw));
        }
        gpw.setGpwObserver(new GPWObserver(gpw));
        return gpw;
    }

    public IndeksBuilder getIndeksBuilder() {
        return indeksBuilder;
    }

    public void setIndeksBuilder(IndeksBuilder indeksBuilder) {
        this.indeksBuilder = indeksBuilder;
    }

    public GPWFactory getGpwFactory() {
        return gpwFactory;
    }

    public void setGpwFactory(GPWFactory gpwFactory) {
        this.gpwFactory = gpwFactory;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
