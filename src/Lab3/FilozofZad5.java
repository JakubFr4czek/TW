package Lab3;

import java.util.concurrent.locks.Lock;

class FilozofZad5 extends Filozof {


    private Arbiter arbiter;

    FilozofZad5(Lock lewyWidelec, Lock prawyWidelec, int id_filozofa, Arbiter arbiter) {
        super(lewyWidelec, prawyWidelec, id_filozofa);
        this.arbiter = arbiter;
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
