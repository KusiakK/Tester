package pl.kk.program;

import pl.kk.annotations.Run;
import pl.kk.annotations.ScanForRun;

@ScanForRun
public class YetAnotherClass {

    @Run
    public void yetAnotherMethod(){
        System.out.println("another method!");
    }

    public void yetAnotherMethodNotToRun(){
        System.out.println("another method not to run!");
    }
}
