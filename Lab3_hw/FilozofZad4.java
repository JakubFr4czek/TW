package Lab3;

import java.util.concurrent.locks.Lock;

// 4 - zakleszczenie z małym prawdopodobieństwem, zagłodzenie

public class FilozofZad4 extends Filozof {

    FilozofZad4(Lock lewyWidelec, Lock prawyWidelec, int id_filozofa) {
        super(lewyWidelec, prawyWidelec, id_filozofa);
    }

    public void run(){

        while(true) {

            mysl();

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

    }
}
