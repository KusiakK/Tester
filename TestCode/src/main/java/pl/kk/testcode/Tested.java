package pl.kk.testcode;

import pl.kk.annotations.Run;
import pl.kk.annotations.Runnable;

@Runnable
public class Tested {

    @Run
    public void runnable() {
        System.out.println("odpalanka się odpala");
    }

    @Run
    void method() {
        System.out.println("metodka się odpala");
    }

    void unRunnable() {
        System.out.println("nieodpalalnka się odpala");
    }
}
