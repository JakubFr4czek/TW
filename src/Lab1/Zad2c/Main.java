package Lab1.Zad2c;

class Liczba{

    static int liczba = 0;

    synchronized public void inkrementuj_1(){
        liczba += 1;
    }


    synchronized static public void inkrementuj_2(){
        liczba += 1;
    }

}

class Watek extends Thread{

    Liczba l;
    int increment_count = 100000;


    Watek(Liczba l){
        this.l = l;
    }

    public void run(){

        for(int i = 0; i < increment_count; i += 1){
            l.inkrementuj_1();
            Liczba.inkrementuj_2();
        }

        System.out.println(this.l);

    }

}

public class Main {

    public static void main(String[] args){

        Liczba l = new Liczba();

        Watek w1 = new Watek(l);
        Watek w2 = new Watek(l);

        w1.start();
        w2.start();

        try{
            w1.join();
            w2.join();
        } catch (InterruptedException err) {
            System.out.println("Thread interrupted");
        }

    }

}
