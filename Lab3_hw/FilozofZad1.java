package Lab3;

import java.util.concurrent.locks.Lock;

// 1 - zagłodzenie i zakleszczenie

public class FilozofZad1 extends Filozof {

    FilozofZad1(Lock lewyWidelec, Lock prawyWidelec, int id_filozofa) {
        super(lewyWidelec, prawyWidelec, id_filozofa);
    }

    public void run(){

        while(true){

            mysl();

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
