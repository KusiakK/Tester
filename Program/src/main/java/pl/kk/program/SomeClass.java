package pl.kk.program;

import pl.kk.annotations.Run;
import pl.kk.annotations.ScanForRun;

@ScanForRun
public class SomeClass {

    @Run
    public void printHello(){
        System.out.println("Hello!");
    }
}
