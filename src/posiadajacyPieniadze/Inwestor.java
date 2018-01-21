package posiadajacyPieniadze;

import main.Main;

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

    @Override
    public void run() {
        int size,i=0,rnd;
        while(true){
            i=0;
            kupInwestycje();
            sprzedajInwestycje();
            System.out.println(this.getName());
            size = Main.getContainer().getHashMapRynkow().keySet().size();
            rnd = (int)(Math.random()*100)%size;
            for (String s:Main.getContainer().getHashMapRynkow().keySet()
                 ) {
                if(i == rnd){

                }
                i++;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
