package Lab1.Zad3;

class Napis{

    String napis = "";

    synchronized public void dodaj_abcd(){
        napis += "a";
    }


    synchronized public void dodaj_1234(){
        napis += "b";
    }

}

class Watek extends Thread{

    Napis n;
    int increment_count = 1000;


    Watek(Napis n){
        this.n = n;
    }

    public void run(){

        for(int i = 0; i < increment_count; i += 1){
            n.dodaj_1234();
            n.dodaj_abcd();
        }

        //System.out.println(this.n.napis);

    }

}

public class Main {

    public static void main(String[] args){

        Napis n = new Napis();

        Watek w1 = new Watek(n);
        Watek w2 = new Watek(n);

        w1.start();
        w2.start();

        try{
            w1.join();
            w2.join();
        } catch (InterruptedException err) {
            System.out.println("Thread interrupted");
        }

        System.out.println(n.napis.length());

    }

}
