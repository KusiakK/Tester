package pl.kk.testcode;

import pl.kk.annotations.MarkedForRun;
import pl.kk.annotations.Run;

public class Tested implements MarkedForRun {

    @Run
    public void runnable() {
        System.out.println("odpalanka się odpala");
    }

    @Run
    public void method() {
        System.out.println("metodka się odpala");
    }

    public void unRunnable() {
        System.out.println("nieodpalalnka się odpala");
    }
}
