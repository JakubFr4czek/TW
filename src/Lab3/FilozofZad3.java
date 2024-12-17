package Lab3;

import java.util.concurrent.locks.Lock;

// 3 - zagłodzenie, ale nie zakleszczenie

public class FilozofZad3 extends Filozof {

    FilozofZad3(Lock lewyWidelec, Lock prawyWidelec, int id_filozofa) {
        super(lewyWidelec, prawyWidelec, id_filozofa);
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

                prawy_widelec.unlock();
                lewy_widelec.unlock();


            } else if (id_filozofa % 2 == 1) {

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
}
