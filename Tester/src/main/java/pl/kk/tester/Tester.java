package pl.kk.tester;

import pl.kk.annotations.Run;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Tester {

    private static final TesterStatistics statistics = new TesterStatistics();

    public static void main(String[] args) {
        Tester tester = new Tester();
        tester.process(tester);
    }

    public void process(Tester testedClass) {

        Method[] methods = testedClass.getClass().getDeclaredMethods();

        for (Method method : methods) {
            try {
                if (method.isAnnotationPresent(Run.class)) method.invoke(testedClass.getClass());
            } catch (IllegalArgumentException e) {
                System.err.format("metoda %s - nieodpowiednia ilość argumentów (%s) %n\n", method, e.getMessage());
            } catch (IllegalAccessException e) {
                System.err.format("metoda %s - dostęp zabroniony? %s przyczyna: %s %n\n", method, e.getMessage(), e.getCause());
            } catch (InvocationTargetException e) {
                System.err.format("metoda %s - wywołanie nieudane z racji na %s przyczyna: %s %n\n", method, e.getMessage(), e.getCause());
            }
        }
        System.out.format("Odpalone metody : %d\n", statistics.methodsRan());
    }

    @Run
    public void runnable() {
        System.out.println("odpalanka się odpala");
    }
}

