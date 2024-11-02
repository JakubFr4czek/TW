package Lab3;// 1 - zagłodzenie i zakleszczenie
// 2 - zakleszczenie nie, zagłodzenia tak
// 3 - zagłodzenie, ale nie zakleszczenie
// 4 - zakleszczenie z małym prawdopodobieństwem, zagłodzenie

// 3 / 4 - rozwinięcie wersji I
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import java.util.ArrayList;
import java.util.List;



class Filozof extends Thread{

    private Lock lewy_widelec;
    private Lock prawy_widelec;

    private int id_filozofa;

    private Arbiter arbiter;

    int wariant = 1;

    Filozof(Lock lewyWidelec, Lock prawyWidelec, int id_filozofa, Arbiter arbiter){
        this.lewy_widelec = lewyWidelec;
        this.prawy_widelec = prawyWidelec;
        this.id_filozofa = id_filozofa;
        this.arbiter = arbiter;
    }

    private void jedz(){

        System.out.println("Filozof " + id_filozofa + " je!");
        try{
            Thread.sleep((int) (Math.random() * 100));
            //Thread.sleep(100);
        }catch(Exception e){
            System.exit(0);   
        }

    }

    private void mysl(){

        System.out.println("Filozof " + id_filozofa + " myśli!");
        try{
            Thread.sleep((int) (Math.random() * 100));
            //Thread.sleep(100);
        }catch(Exception e){
            System.exit(0);   
        }
        
    }

    public void run(){

        while(true){


            mysl();

            if(wariant == 1){

                lewy_widelec.lock();

                System.out.println("Filozof " + id_filozofa + " podniósł lewy widelec!");

                prawy_widelec.lock();

                System.out.println("Filozof " + id_filozofa + " podniósł prawy widelec!");
                jedz();
                prawy_widelec.unlock();
                lewy_widelec.unlock();

            }else if(wariant == 2){

                if(lewy_widelec.tryLock()){

                    if(prawy_widelec.tryLock()){
                    
                    System.out.println("Filozof " + id_filozofa + " podniósł lewy widelec!");
                    System.out.println("Filozof " + id_filozofa + " podniósł prawy widelec!");

                    jedz();
                    prawy_widelec.unlock();
                    }
                    lewy_widelec.unlock();

                }

            }else if(wariant == 3){

                if(id_filozofa %2 == 0){


                    prawy_widelec.lock();

                    System.out.println("Filozof " + id_filozofa + " podniósł lewy widelec!");

                    lewy_widelec.lock();

                    System.out.println("Filozof " + id_filozofa + " podniósł prawy widelec!");
                    jedz();
                    prawy_widelec.unlock();
                    lewy_widelec.unlock();


                }else if(id_filozofa % 2 == 1){

                    lewy_widelec.lock();

                    System.out.println("Filozof " + id_filozofa + " podniósł lewy widelec!");

                    prawy_widelec.lock();

                    System.out.println("Filozof " + id_filozofa + " podniósł prawy widelec!");
                    jedz();
                    prawy_widelec.unlock();
                    lewy_widelec.unlock();

                }

            }else if(wariant == 4){

                if(Math.random() < 0.5){

                    prawy_widelec.lock();

                    System.out.println("Filozof " + id_filozofa + " podniósł lewy widelec!");

                    lewy_widelec.lock();

                    System.out.println("Filozof " + id_filozofa + " podniósł prawy widelec!");
                    jedz();
                    prawy_widelec.unlock();
                    lewy_widelec.unlock();

                }else{

                    lewy_widelec.lock();

                    System.out.println("Filozof " + id_filozofa + " podniósł lewy widelec!");

                    prawy_widelec.lock();

                    System.out.println("Filozof " + id_filozofa + " podniósł prawy widelec!");
                    jedz();
                    prawy_widelec.unlock();
                    lewy_widelec.unlock();

                }

            }
            else if(wariant == 5){

                if(arbiter.zapytaj_czy_mozesz_poniesc_widelec()){

                    lewy_widelec.lock();

                    System.out.println("Filozof " + id_filozofa + " podniósł lewy widelec!");

                    prawy_widelec.lock();

                    System.out.println("Filozof " + id_filozofa + " podniósł prawy widelec!");
                    jedz();
                    prawy_widelec.unlock();
                    lewy_widelec.unlock();

                }

            }

        }

            /*
            if(lewy_widelec.tryLock()){
                System.out.println("Filozof " + id_filozofa + " podniósł lewy widelec!");

                while(true){

                    if(prawy_widelec.tryLock()){
                        System.out.println("Filozof " + id_filozofa + " podniósł prawy widelec!");
                        jedz();
                        prawy_widelec.unlock();
                        lewy_widelec.unlock();
                        break;
                    }

                }
            }*/

        }
    }
    

class Arbiter{

        private int liczba_filozofow;
        private int liczba_filozofow_z_widelcami;
    
        Arbiter(int liczba_filozofow){
            this.liczba_filozofow = liczba_filozofow;
            this.liczba_filozofow_z_widelcami = 0;
            
        }
    
        public boolean zapytaj_czy_mozesz_poniesc_widelec(){
    
            if(liczba_filozofow_z_widelcami < liczba_filozofow){
                liczba_filozofow_z_widelcami += 1;
                return true;
            }return false;
    
        }
    }
// semafora do 5

public class Main {
    public static void main(String[] args) {

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
