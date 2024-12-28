import java.io.PipedReader;
import java.io.PipedWriter;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.concurrent.*;

class MyThread extends Thread{

    private PipedReader pr;
    private PipedWriter pw;

    private ArrayList<Character> buffer = new ArrayList<>();
    private int buffer_size = 10;

    public MyThread(PipedReader pr, PipedWriter pw, ArrayList<Character> buffer){

        this.pr = pr;
        this.pw = pw;
        if (buffer != null)
            this.buffer = buffer;

    }

    public synchronized void run(){

        while(true){

            try{

            if(pr != null){
            
                while(buffer.size() >= buffer_size){
                    wait();
                }

                char ch  = (char)pr.read();

                buffer.add(ch);

               

                notifyAll();

                if(pw != null){

                    while(buffer.size() == 0){
                        wait();
                    }

                    pw.write(buffer.get(buffer.size() - 1));
                    buffer.remove(buffer.size() - 1);
                    notifyAll();

                    

                }else{

                    System.out.println("Przeczytałem: " + ch);
                }


            }else{

                while(buffer.size() == 0){
                    wait();
                }

                char ch = buffer.get(buffer.size() - 1);
                buffer.remove(buffer.size() - 1);

                pw.write(ch);
                notifyAll();

            }

            
            }catch(Exception e){
                System.out.println(e);
            }

        }


    }

}

public class Main {

    public static void main(String[] args){

        try{

        PipedWriter pw1 = new PipedWriter();
        PipedWriter pw2 = new PipedWriter();

        
        PipedReader pr1 = new PipedReader(pw1);
        PipedReader pr2 = new PipedReader(pw2);

        ArrayList<Character> buffer = new ArrayList<>();

        buffer.add('a');
        buffer.add('l');
        buffer.add('a');
        buffer.add('m');
        buffer.add('a');
        buffer.add('k');
        buffer.add('o');
        buffer.add('t');
        buffer.add('a');

        MyThread t1 = new MyThread(null, pw1, buffer);
        MyThread t2 = new MyThread(pr1, pw2, null);
        MyThread t3 = new MyThread(pr2, null, null);

        t1.start();
        t2.start();
        t3.start();

            t1.join();
            t2.join();
            t3.join();
        }catch(Exception e){}

    }

}
