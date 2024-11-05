package Lab3.Zad3;

// 3 - zagłodzenie, ale nie zakleszczenie

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import java.util.ArrayList;
import java.util.List;

class Filozof extends Thread{

    private Lock lewy_widelec;
    private Lock prawy_widelec;

    public int id_filozofa;
    public long zjadlem;
    public long czekalem_w_sumie;
    private long start_czekania;

    Filozof(Lock lewyWidelec, Lock prawyWidelec, int id_filozofa){
        this.lewy_widelec = lewyWidelec;
        this.prawy_widelec = prawyWidelec;
        this.id_filozofa = id_filozofa;
        this.czekalem_w_sumie = 0;
        this.start_czekania = 0;
        this.zjadlem = 0;
    }

    private void jedz(){

        System.out.println("Filozof " + id_filozofa + " je!");

    }

    private void mysl(){

        if(start_czekania == 0)
            start_czekania = System.nanoTime();

        System.out.println("Filozof " + id_filozofa + " myśli!");

    }

    public void run(){

        while(true) {

            mysl();

            if (id_filozofa % 2 == 0) {


                prawy_widelec.lock();

                System.out.println("Filozof " + id_filozofa + " podniósł lewy widelec!");

                lewy_widelec.lock();

                System.out.println("Filozof " + id_filozofa + " podniósł prawy widelec!");
                jedz();
                czekalem_w_sumie += System.nanoTime() - start_czekania;
                zjadlem += 1;
                prawy_widelec.unlock();
                lewy_widelec.unlock();


            } else if (id_filozofa % 2 == 1) {

                lewy_widelec.lock();

                System.out.println("Filozof " + id_filozofa + " podniósł lewy widelec!");

                prawy_widelec.lock();

                System.out.println("Filozof " + id_filozofa + " podniósł prawy widelec!");
                jedz();
                czekalem_w_sumie += System.nanoTime() - start_czekania;
                zjadlem += 1;
                prawy_widelec.unlock();
                lewy_widelec.unlock();

            }

        }

    }
}

class Statystyki extends Thread{

    private List<Filozof> filozofowie = new ArrayList<>();

    public void sledz_filozofa(Filozof filozof){
        filozofowie.add(filozof);
    }

    public void run(){

        while(true){



            try (BufferedWriter writer = new BufferedWriter(new FileWriter("zad3.txt"))) {
                for (Filozof filozof : filozofowie) {

                    writer.write(filozof.id_filozofa + " " + filozof.zjadlem + " " + filozof.czekalem_w_sumie);
                    writer.newLine();

                }
            } catch (IOException e) {
                System.exit(0);
            }


            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }

    }

}

public class Main {
    public void main(String[] args) {

        int n = 1000;

        List<Lock> widelce = new ArrayList<Lock>();
        List<Lab3.Zad3.Filozof> filozofowie = new ArrayList<Lab3.Zad3.Filozof>();

        for(int i = 0; i < n; i += 1){

            widelce.add(new ReentrantLock());

        }

        for(int i = 0; i < n; i += 1){

            Lab3.Zad3.Filozof nowy_filozof;

            if(i + 1 < n){
                nowy_filozof = new Lab3.Zad3.Filozof(widelce.get(i), widelce.get(i+1), i);
            }else{
                nowy_filozof = new Lab3.Zad3.Filozof(widelce.get(i), widelce.get(0), i);
            }

            filozofowie.add(nowy_filozof);

        }

        for(int i = 0; i < n; i += 1){
            filozofowie.get(i).start();
        }

        Statystyki statystyki = new Statystyki();

        for(Filozof filozof : filozofowie){
            statystyki.sledz_filozofa(filozof);
        }

        statystyki.start();

    }
}
