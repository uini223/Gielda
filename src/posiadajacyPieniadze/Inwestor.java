package posiadajacyPieniadze;

public class Inwestor extends PosiadajacyPieniadze {
    int pesel;

    public Inwestor() {
        super();
        pesel = (int)(Math.random()*10000000);
    }

    public int getPesel() {
        return pesel;
    }

    public void setPesel(int pesel) {
        this.pesel = pesel;
    }

    @Override

    public void kupInwestycje() {

    }

    @Override
    public void sprzedajInwestycje() {

    }
    public void kupJednostkiUczestictwa() {

    }
    public void sperzedajJednostkiUczestnictwa(){

    }
}
