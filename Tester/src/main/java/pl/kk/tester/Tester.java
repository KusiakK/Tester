package pl.kk.tester;

import pl.kk.annotations.Run;
import pl.kk.annotations.Runnable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public class Tester {

    private static final TesterStatistics statistics = new TesterStatistics();
    private static final Wizard wizard = new Wizard();

    public void process() {
        Iterable<Class<?>> annotatedClasses = wizard.getAnnotatedClasses(Runnable.class);
        Map<Class<?>, List<Method>> annotatedMethods = wizard.getAnnotatedMethods(annotatedClasses, Run.class);

        for (Map.Entry<Class<?>, List<Method>> entry : annotatedMethods.entrySet()) {
            for (Method method : entry.getValue()) {
                try {
                    method.invoke(entry.getKey());
                } catch (IllegalArgumentException e) {
                    System.err.format("metoda %s - nieodpowiednia ilość argumentów (%s) %n", method, e.getMessage());
                } catch (IllegalAccessException e) {
                    System.err.format("metoda %s - dostęp zabroniony? %s przyczyna: %s %n", method, e.getMessage(), e.getCause());
                } catch (InvocationTargetException e) {
                    System.err.format("metoda %s - wywołanie nieudane z racji na %s przyczyna: %s %n", method, e.getMessage(), e.getCause());
                }
                System.out.format("Odpalone metody : %d", statistics.methodsRan());
            }
        }
    }
}
