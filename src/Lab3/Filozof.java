package Lab3;

import java.util.concurrent.locks.Lock;
import java.lang.Math;

public abstract class Filozof extends Thread{

        protected Lock lewy_widelec;
        protected Lock prawy_widelec;

        public long id_filozofa;
        public long zjadlem;
        public double czekalem_w_sumie; // w sekundach
        public long start_czekania; // w milisekundach

        protected Filozof(Lock lewyWidelec, Lock prawyWidelec, int id_filozofa){
            this.lewy_widelec = lewyWidelec;
            this.prawy_widelec = prawyWidelec;
            this.id_filozofa = id_filozofa;
            this.zjadlem = 0;
            this.czekalem_w_sumie = 0;
            this.start_czekania = 0;
        }

        protected void jedz(){

            System.out.println("Filozof " + id_filozofa + " je!");

            zjadlem += 1;
            czekalem_w_sumie += (double)(System.nanoTime() - start_czekania) / Math.pow(10, 9);
            start_czekania = 0;

        }

        protected void mysl(){

            if(start_czekania == 0)
                start_czekania = System.nanoTime();

            System.out.println("Filozof " + id_filozofa + " myśli!");

        }

        public abstract void run();

}
