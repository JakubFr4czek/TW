package Lab3;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Test {

    public void run_test(Class<? extends Filozof> filozofClass, int n, String filename) {
        List<Lock> widelce = new ArrayList<>();
        List<Filozof> filozofowie = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            widelce.add(new ReentrantLock());
        }

        for (int i = 0; i < n; i++) {
            Filozof nowy_filozof;
            Lock leftFork = widelce.get(i);
            Lock rightFork = widelce.get((i + 1) % n);

            try {

                nowy_filozof = filozofClass.getDeclaredConstructor(Lock.class, Lock.class, int.class)
                        .newInstance(leftFork, rightFork, i);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }

            filozofowie.add(nowy_filozof);
        }

        for (Filozof filozof : filozofowie) {
            filozof.start();
        }

        Statystyki statystyki = new Statystyki(filename);
        for (Filozof filozof : filozofowie) {
            statystyki.sledz_filozofa(filozof);
        }
        statystyki.start();
    }

}
