package Lab3;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Statystyki extends Thread{

    private List<Filozof> filozofowie = new ArrayList<>();
    private String filename;


    public Statystyki(String filename){
        this.filename = filename;
    }

    public void sledz_filozofa(Filozof filozof){
        filozofowie.add(filozof);
    }

    public void run(){

        while(true){

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
                for (Filozof filozof : filozofowie) {

                    writer.write(filozof.id_filozofa + " " + filozof.zjadlem + " " + filozof.czekalem_w_sumie);
                    writer.newLine();

                }
            } catch (IOException e) {
                System.exit(0);
            }


            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }

    }

}