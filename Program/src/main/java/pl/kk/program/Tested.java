package pl.kk.program;

import pl.kk.annotations.Run;

public class Tested {

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
