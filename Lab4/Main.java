package Lab4;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class Node{

    Object objectRef;
    Node next;
    ReentrantReadWriteLock lock;

    Node(Object objectRef, Node next, ReentrantReadWriteLock lock){

        this.objectRef = objectRef;
        this.next = next;
        this.lock = lock;

    }

}

class LinkedList{

    Node head;
    int max_size = 10;
    int current_size = 0;

    final Lock lock = new ReentrantLock();
    final Condition notFull  = lock.newCondition();
    final Condition notEmpty = lock.newCondition();

    LinkedList(){
        head = new Node(null, null, new ReentrantReadWriteLock());
    }

    public void add(Object objectRef){

        lock.lock();

        try {
            while (current_size == max_size)
                notFull.await();

            this.head.lock.writeLock().lock();
            Node temp = this.head;

            while (temp.next != null) {

                temp.next.lock.writeLock().lock();

                temp.lock.writeLock().unlock();

                temp = temp.next;

            }

            temp.next = new Node(objectRef, null, new ReentrantReadWriteLock());

            current_size += 1;

            temp.lock.writeLock().unlock();

            notEmpty.signal();

        }catch(Exception e){
            System.exit(1);
        } finally {
            lock.unlock();
        }

    }

    public boolean contains(Object objectRef){

        this.head.lock.readLock().lock();

        Node temp = this.head;

        while(temp.next != null){

            temp.next.lock.readLock().lock();


            temp.lock.readLock().unlock();

            temp = temp.next;

            if(temp.objectRef == objectRef){

                temp.lock.readLock().unlock();

                return true;

            }

        }

        if(temp != head){
            temp.lock.readLock().unlock();
        }

        return false;

    }

    public boolean remove(Object objectRef){

        lock.lock();

        try {

            while (current_size == 0)
                notEmpty.await();

            this.head.lock.writeLock().lock();
            Node temp = this.head;

            while (temp.next != null) {

                temp.next.lock.writeLock().lock();

                if (temp.next.objectRef == objectRef) {

                    Node toBeDeleted = temp.next;
                    temp.next = toBeDeleted.next;

                    current_size -= 1;

                    toBeDeleted.lock.writeLock().unlock();
                    temp.lock.writeLock().unlock();

                    notFull.signal();
                    return true;

                }

                temp.lock.writeLock().unlock();

                temp = temp.next;

            }

            temp.lock.writeLock().unlock();
        }catch(Exception e){
            System.exit(1);
        } finally {
            lock.unlock();
        }

        return false;

    }

    public void printList(){

        this.head.lock.readLock().lock();

        Node temp = this.head;

        synchronized(this) { // tylko do sprawdzenia, czy działa

            while (temp.next != null) {

                temp.next.lock.readLock().lock();

                temp.lock.readLock().unlock();

                temp = temp.next;

                System.out.print(temp.objectRef + " ");

            }

            System.out.println();
        }
        temp.lock.readLock().unlock();

    }


}


class Writer extends Thread{

    LinkedList ll;
    boolean adding = false;

    Writer(LinkedList ll, boolean adding){
        this.ll = ll;
        this.adding = adding;
    }

    public void run(){

        while (true) {


            if (adding) {

                for (int i = 0; i < 10; i += 1) {

                    ll.add(i);

                }

            } else {
                for (int i = 0; i < 10; i += 1) {

                    ll.remove(i);

                }
            }

        }

    }

}

class Reader extends Thread{

    LinkedList ll;

    Reader(LinkedList ll){
        this.ll = ll;
    }

    public void run(){

        for(int i = 0; i < 10; i += 1){

            while(true) {
                //System.out.println(ll.contains(i));
                ll.printList();
            }
        }

    }

}

public class Main {

    public static void main(String[] args) {

        LinkedList ll = new LinkedList();

        for(int i = 0; i < 10; i += 1){
            Writer w = new Writer(ll,true);
            w.start();
        }

        for(int i = 0; i < 10; i += 1){
            Writer w = new Writer(ll,false);
            w.start();
        }

        for(int i = 0; i < 10; i += 1){
            Reader r = new Reader(ll);
            r.start();
        }



    }

}
