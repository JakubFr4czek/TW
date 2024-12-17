class Rewolwerowiec extends Thread{

    int liczba;
    static volatile boolean alive = true;

    Rewolwerowiec(int liczba){
        this.liczba = liczba;
    }

    public void run(){

        for(int i = liczba; i > 0; i -= 1){
            if(alive)
                System.out.println(i);
            else Thread.currentThread().interrupt();
        }

        synchronized(Rewolwerowiec.class){
            if(alive){
                System.out.println("Pif! Paf!");
                alive = false;
            }else{
                Thread.currentThread().interrupt();
            }
        }
        

    }

}

public class Main {

    public static void main(String[] args){

        for(int i = 0; i < 10; i += 1){

            Rewolwerowiec rew = new Rewolwerowiec(2);
            rew.start();

        }

    }

    
}
