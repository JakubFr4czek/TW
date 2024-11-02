package Lab1.Przyklad;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

 /*class MojWatek extends Thread {
        
        Balans b;

        MojWatek(Balans b){this.b = b;}

        public void run() {
            System.out.println("Wątek" + Thread.currentThread().getId());

            for(int i = 0; i < 100000; i += 1){
                int x = b.foo();
                if (x != 0){
                    System.out.println(x);
                    break;
                }
            }
        }
    }*/

class MojWatek implements Runnable {
    
    Balans b;

    MojWatek(Balans b) {
        this.b = b;
    }

    public void run() {
        System.out.println("Wątek " + Thread.currentThread().getId());

        for (int i = 0; i < 100000; i += 1) {
            int x = b.foo();
            if (x != 0) {
                System.out.println(x);
                break;
            }
        }
    }
}

class Balans {
    int x = 0;

    public int foo() {
        //synchronized (this) {
            x++;
            x--;
            return x;
        //}
    }
}

public class Main {
    public static void main(String[] args) { 
        System.out.println("Start");

        Balans balans = new Balans();

        ExecutorService exec = Executors.newCachedThreadPool();

        for(int i=0; i < 20; i += 1){
            MojWatek mw = new MojWatek(balans);
            exec.execute(mw);
        }

        exec.shutdown(); // Shutdown oznacza join

        /*Thread mw1 = new Thread(new MojWatek(balans));
        Thread mw2 = new Thread(new MojWatek(balans)); 
        mw1.start();
        mw2.start();
        try {
            mw1.join(); 
            mw2.join();
        } catch (InterruptedException err) {
            System.out.println("Thread interrupted");
        }
        */
        System.out.println("Koniec");
    }
}
