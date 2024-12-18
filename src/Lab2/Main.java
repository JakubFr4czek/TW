import java.util.Deque;
import java.util.ArrayDeque;

class Producer extends Thread {
    private Buffer _buf;
    private int id;
    Producer(Buffer buffer, int id) {
        this._buf = buffer;
        this.id = id;
    }

    public void run() {
        for (int i = 0; i < 100; ++i) {
            _buf.put(id * i);
            try {
                Thread.sleep(10);
            } catch (Exception e) {
                System.exit(0);
            }
        }
    }
}

class Consumer extends Thread {
    private Buffer _buf;

    Consumer(Buffer buffer) {
        this._buf = buffer;
    }

    public void run() {
        for (int i = 0; i < 100; ++i) {
            //System.out.println(_buf.get());
            _buf.get();
            try {
                Thread.sleep(20);
            } catch (Exception e) {
                System.exit(0);
            }
        }
    }
}

class Buffer {
    private Deque<Integer> _queue = new ArrayDeque<>();

    public synchronized void put(int i) {

            while (_queue.size() >= 10) {
                try {
                    System.out.println("Queue full!");
                    wait();
                } catch (Exception e) {
                    System.exit(0);
                }
            }
            _queue.push(i); // <=> toggle status

            System.out.println("Producent " + Thread.currentThread().threadId() + " wyprodukował " + i);
            notifyAll();

    }

    public synchronized int get() {

            while (_queue.isEmpty()) {
                try {
                    System.out.println("Queue empty!");
                    wait();
                } catch (Exception e) {
                    System.exit(0);
                }
            }
            int item = _queue.pop(); // <=> toggle status
            
            System.out.println("Konsument " + Thread.currentThread().threadId() + " zjadł " + item);
            notifyAll();
            return item;
    }
}

public class Main {
    public static void main(String[] args) {
        Buffer buffer = new Buffer();
 
        //wersja 1
        
        Producer producer = new Producer(buffer, 1);
        Consumer consumer = new Consumer(buffer);

        producer.start();
        consumer.start();
        

        //wersja 2
        /*
        for(int i = 0; i < 10; i += 1){
            Producer new_producer = new Producer(buffer,  1000 * (i + 1));
            new_producer.start();
        }

        Consumer consumer = new Consumer(buffer);

        consumer.start();

        */

        //wersja 3

        /*
        Producer producer = new Producer(buffer, 1);

        producer.start();

        for(int i = 0; i < 10; i += 1){
            Consumer new_consumer = new Consumer(buffer);
            new_consumer.start();
        }
        */
        //wersja 4
        /*
        for(int i = 0; i < 10; i += 1){
            Producer new_producer = new Producer(buffer, 1000 * (i + 1));
            new_producer.start();
        }

        for(int i = 0; i < 10; i += 1){
            Consumer new_consumer = new Consumer(buffer);
            new_consumer.start();
        }
        */
            
        

    }
}