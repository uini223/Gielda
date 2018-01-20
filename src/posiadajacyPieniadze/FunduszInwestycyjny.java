package posiadajacyPieniadze;


public class FunduszInwestycyjny extends PosiadajacyPieniadze {
    private String nazwa;

    public String getNazwa() {
        return nazwa;
    }

    public FunduszInwestycyjny() {
        super();
        nazwa = getName();
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    @Override
    public void kupInwestycje() {

    }

    @Override
    public void sprzedajInwestycje() {

    }
}
