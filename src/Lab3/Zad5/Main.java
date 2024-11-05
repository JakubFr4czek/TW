package Lab3.Zad5;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import java.util.ArrayList;
import java.util.List;

class Filozof extends Thread{

    private Lock lewy_widelec;
    private Lock prawy_widelec;

    private int id_filozofa;

    private Arbiter arbiter;

    Filozof(Lock lewyWidelec, Lock prawyWidelec, int id_filozofa, Arbiter arbiter){
        this.lewy_widelec = lewyWidelec;
        this.prawy_widelec = prawyWidelec;
        this.id_filozofa = id_filozofa;
        this.arbiter = arbiter;
    }

    private void jedz(){

        System.out.println("Filozof " + id_filozofa + " je!");
        try{
            //Thread.sleep((int) (Math.random() * 100));
            Thread.sleep(100);
        }catch(Exception e){
            System.exit(0);
        }

    }

    private void mysl(){

        System.out.println("Filozof " + id_filozofa + " myśli!");
        try{
           // Thread.sleep((int) (Math.random() * 100));
            Thread.sleep(100);
        }catch(Exception e){
            System.exit(0);
        }

    }

    public void run(){

        while(true) {


            mysl();

            try {
                arbiter.semafor_arbitra.acquire();

                if (!arbiter.zapytaj_czy_mozesz_podniesc_widelec(this)) {
                    System.out.println("Filozof " + id_filozofa + " musi czekać na pozwolenia Arbitra");
                    continue;
                }

            }catch (InterruptedException e) {
                System.exit(0);
            }finally {
                arbiter.semafor_arbitra.release();
            }

            lewy_widelec.lock();

            System.out.println("Filozof " + id_filozofa + " podniósł lewy widelec!");


            prawy_widelec.lock();

            System.out.println("Filozof " + id_filozofa + " podniósł prawy widelec!");
            jedz();
            prawy_widelec.unlock();
            lewy_widelec.unlock();

            try {

                arbiter.semafor_arbitra.acquire();

                arbiter.poinformuj_ze_zjadles(this);

            }catch (InterruptedException e) {
                System.exit(0);
            }finally {
                arbiter.semafor_arbitra.release();
            }



        }

    }
}


class Arbiter{

    private int liczba_filozofow;
    private List<Filozof> filozofowie_z_widelcami = new ArrayList<>();
    private Filozof oczekujacy_filozof;

    Semaphore semafor_arbitra;

    Arbiter(int liczba_filozofow){
        this.liczba_filozofow = liczba_filozofow;
        this.semafor_arbitra = new Semaphore(1);
    }

    public boolean zapytaj_czy_mozesz_podniesc_widelec(Filozof filozof){

        if(filozofowie_z_widelcami.contains(filozof)) {

            return true;

        }else {

            if (filozofowie_z_widelcami.size() + 1 < liczba_filozofow) {

                    if (oczekujacy_filozof == null) {

                        filozofowie_z_widelcami.add(filozof);
                        return true;

                    }else if(oczekujacy_filozof == filozof){

                        filozofowie_z_widelcami.add(filozof);
                        oczekujacy_filozof = null;
                        return true;
                    }


            }else{
                oczekujacy_filozof = filozof;
            }

        }

        return false;
    }


    public void poinformuj_ze_zjadles(Filozof filozof){

        filozofowie_z_widelcami.remove(filozof);

    }

}

public class Main {
    public void main(String[] args) {

        int n = 3;

        List<Lock> widelce = new ArrayList<Lock>();
        List<Filozof> filozofowie = new ArrayList<Filozof>();
        Arbiter arbiter = new Arbiter(n);

        for(int i = 0; i < n; i += 1){

            widelce.add(new ReentrantLock());

        }

        for(int i = 0; i < n; i += 1){

            Filozof nowy_filozof;

            if(i + 1 < n){
                nowy_filozof = new Filozof(widelce.get(i), widelce.get(i+1), i, arbiter);
            }else{
                nowy_filozof = new Filozof(widelce.get(i), widelce.get(0), i, arbiter);
            }

            filozofowie.add(nowy_filozof);

        }

        for(int i = 0; i < n; i += 1){
            filozofowie.get(i).start();
        }

    }
}
