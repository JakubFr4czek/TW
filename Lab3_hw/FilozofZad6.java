package Lab3;

import java.util.concurrent.locks.Lock;

class FilozofZad6 extends Filozof {


    private Arbiter arbiter;
    private boolean jestem_specjalny;

    FilozofZad6(Lock lewyWidelec, Lock prawyWidelec, int id_filozofa, Arbiter arbiter) {
        super(lewyWidelec, prawyWidelec, id_filozofa);
        this.arbiter = arbiter;
        this.jestem_specjalny = false;
    }


    public void run(){

        while(true) {

            mysl();

            try {
                arbiter.semafor_arbitra.acquire();

                if (!arbiter.zapytaj_czy_mozesz_podniesc_widelec(this)) {
                    if(arbiter.oczekujacy_filozof == this) {
                        jestem_specjalny = true;
                        System.out.println("Filozof " + id_filozofa + " jest specjalny ");
                    }else{
                        System.out.println("Filozof " + id_filozofa + " musi zaczekać, aż filozof, który był poprzednio specjalny zasiądzie do stołu.");
                        continue;
                    }
                }

            }catch (InterruptedException e) {
                System.exit(0);
            }finally {
                arbiter.semafor_arbitra.release();
            }

            if(jestem_specjalny){
                prawy_widelec.lock();
                System.out.println("Filozof " + id_filozofa + " podniósł prawy widelec!");
            }else{
                lewy_widelec.lock();
                System.out.println("Filozof " + id_filozofa + " podniósł lewy widelec!");
            }



            if(jestem_specjalny){
                lewy_widelec.lock();
                System.out.println("Filozof " + id_filozofa + " podniósł lewy widelec!");
            }else{
                prawy_widelec.lock();
                System.out.println("Filozof " + id_filozofa + " podniósł prawy widelec!");
            }

            jedz();

            prawy_widelec.unlock();
            lewy_widelec.unlock();

            try {

                arbiter.semafor_arbitra.acquire();

                arbiter.poinformuj_ze_zjadles(this);

                jestem_specjalny = false;

            }catch (InterruptedException e) {
                System.exit(0);
            }finally {
                arbiter.semafor_arbitra.release();
            }



        }

    }
}