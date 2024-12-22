package Lab3;

import java.util.concurrent.locks.Lock;

public class FilozofZad2 extends Filozof {

    FilozofZad2(Lock lewyWidelec, Lock prawyWidelec, int id_filozofa) {
        super(lewyWidelec, prawyWidelec, id_filozofa);
    }

    public void run(){

        while(true){

            mysl();

            if(lewy_widelec.tryLock()){

                if(prawy_widelec.tryLock()){

                    System.out.println("Filozof " + id_filozofa + " podniósł lewy widelec!");
                    System.out.println("Filozof " + id_filozofa + " podniósł prawy widelec!");

                    jedz();

                    prawy_widelec.unlock();
                }
                lewy_widelec.unlock();

            }

        }

    }
}
