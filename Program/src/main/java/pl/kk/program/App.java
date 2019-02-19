package pl.kk.program;

import pl.kk.annotations.Run;
import pl.kk.tester.Tester;

import java.lang.reflect.InvocationTargetException;

public class App {
    public static void main(String[] args) {
        try {
            new Tester().process(Tested.class, Run.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
}
