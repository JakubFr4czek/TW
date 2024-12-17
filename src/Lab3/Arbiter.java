package Lab3;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Arbiter{

    private int liczba_filozofow;
    private List<Filozof> filozofowie_z_widelcami = new ArrayList<>();
    public Filozof oczekujacy_filozof;

    public Semaphore semafor_arbitra;

    public Arbiter(int liczba_filozofow){
        this.liczba_filozofow = liczba_filozofow;
        this.semafor_arbitra = new Semaphore(1);
    }

    public boolean zapytaj_czy_mozesz_podniesc_widelec(Filozof filozof){

        if(filozofowie_z_widelcami.contains(filozof)) {

            return true;

        }else {

            if (filozofowie_z_widelcami.size() + 1 < liczba_filozofow) {

                if(oczekujacy_filozof == null) {
                    filozofowie_z_widelcami.add(filozof);
                    return true;
                }else if(oczekujacy_filozof == filozof){
                    filozofowie_z_widelcami.add(filozof);
                    oczekujacy_filozof = null;
                    return true;
                }else{
                    return false;
                }
            }else{
                oczekujacy_filozof = filozof;
                return false;
            }

        }

    }


    public void poinformuj_ze_zjadles(Filozof filozof){

        filozofowie_z_widelcami.remove(filozof);

    }

}
