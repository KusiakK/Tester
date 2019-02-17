package pl.kk.tester;

import pl.kk.annotations.MarkedForRun;
import pl.kk.annotations.Run;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Tester {

    private static final TesterStatistics statistics = new TesterStatistics();

    public void process(MarkedForRun testedClass) {

        Method[] methods = testedClass.getClass().getDeclaredMethods();

        for (Method method : methods) {
            try {
                if (method.isAnnotationPresent(Run.class)) {
                    method.invoke(testedClass);
                    statistics.addSuccessfulRun();
                }
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
}

