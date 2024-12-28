package pl.edu.agh.macwozni.dmeshparallel;

import pl.edu.agh.macwozni.dmeshparallel.myProductions.*;
import pl.edu.agh.macwozni.dmeshparallel.mesh.Vertex;
import pl.edu.agh.macwozni.dmeshparallel.mesh.GraphDrawer;
import pl.edu.agh.macwozni.dmeshparallel.parallelism.BlockRunner;
import pl.edu.agh.macwozni.dmeshparallel.production.PDrawer;

import java.util.Vector;

public class Executor extends Thread {
    
    private final BlockRunner runner;
    
    public Executor(BlockRunner _runner){
        this.runner = _runner;
    }

    @Override
    public void run() {

        /*

        // |e|1 - |e|2 - |e|1

        PDrawer drawer = new GraphDrawer();

        Vertex s = new Vertex(null, null, "S");

        P1 p1 = new P1(s, drawer);

        this.runner.addThread(p1);
        this.runner.startAll();

        P3 p3 = new P3(p1.getObj().getRight(), drawer);
        P5 p5a = new P5(p1.getObj(), drawer);

        this.runner.addThread(p3);
        this.runner.addThread(p5a);
        this.runner.startAll();


        P6 p6 = new P6(p3.getObj(), drawer);
        P5 p5b = new P5(p3.getObj().getRight(), drawer);

        this.runner.addThread(p6);
        this.runner.addThread(p5b);
        this.runner.startAll();

        System.out.println("Done");
        drawer.draw(p5b.getObj());

        */

        /*

        // |e|1 - |e|2 - |e2| - |e|1

        PDrawer drawer = new GraphDrawer();
        //axiom
        Vertex s = new Vertex(null, null, "S");

        //p1 
        P1 p1 = new P1(s, drawer);
        this.runner.addThread(p1);

        //start threads
        this.runner.startAll();

        //p2,p3
        P2 p2 = new P2(p1.getObj(), drawer);
        P3 p3 = new P3(p1.getObj().getRight(), drawer);
        this.runner.addThread(p2);
        this.runner.addThread(p3);

        //start threads
        this.runner.startAll();

        //p5^2,p6^2
        P5 p5A = new P5(p2.getObj(), drawer);
        P5 p5B = new P5(p3.getObj().getRight(), drawer);
        P6 p6A = new P6(p2.getObj().getRight(), drawer);
        P6 p6B = new P6(p3.getObj(), drawer);
        this.runner.addThread(p5A);
        this.runner.addThread(p5B);
        this.runner.addThread(p6A);
        this.runner.addThread(p6B);

        //start threads
        this.runner.startAll();

        //done
        System.out.println("done");
        drawer.draw(p6B.getObj());*/

        // |e|1 - |e|2 - |e|2 - |e|2 - |e|1

        PDrawer drawer = new GraphDrawer();
        //axiom
        Vertex s = new Vertex(null, null, "S");

        P1 p1 = new P1(s, drawer);
        this.runner.addThread(p1);
        this.runner.startAll();

        P3 p3 = new P3(p1.getObj().getRight(), drawer);
        P5 p5a = new P5(p1.getObj(), drawer);

        this.runner.addThread(p3);
        this.runner.addThread(p5a);
        this.runner.startAll();

        P5 p5b = new P5(p3.getObj().getRight(), drawer);
        P4 p4 = new P4(p3.getObj(), drawer);

        this.runner.addThread(p5b);
        this.runner.addThread(p4);
        this.runner.startAll();

        P4 p4hat = new P4(p4.getObj().getRight(), drawer);
        P6 p6 = new P6(p4.getObj(), drawer);

        this.runner.addThread(p4hat);
        this.runner.addThread(p6);
        this.runner.startAll();

        P6 p6hat = new P6(p4hat.getObj().getRight(), drawer);
        P6 p6hathat = new P6(p4hat.getObj(), drawer);

        this.runner.addThread(p6hat);
        this.runner.addThread(p6hathat);
        this.runner.startAll();

        System.out.println("Done");
        drawer.draw(p6hathat.getObj());

    }
}
