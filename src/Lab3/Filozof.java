package Lab3;

import java.util.concurrent.locks.Lock;

public abstract class Filozof extends Thread{

        protected Lock lewy_widelec;
        protected Lock prawy_widelec;

        public long id_filozofa;
        public long zjadlem;
        public long czekalem_w_sumie;
        public long start_czekania;

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

        }

        protected void mysl(){

            System.out.println("Filozof " + id_filozofa + " myśli!");

        }

        public abstract void run();

}
