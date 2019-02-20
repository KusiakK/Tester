package pl.kk.tester;


import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Tester {

    private final TesterStatistics statistics;

    public Tester(TesterStatistics statistics) {
        this.statistics = statistics;
    }

    public void process(Class<?> testedClass, Class<? extends Annotation> annotation) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        Object testObject = testedClass.getConstructor().newInstance();
        Method[] methods = testedClass.getDeclaredMethods();

        for (Method method : methods) {
            try {
                if (method.isAnnotationPresent(annotation)) {
                    method.invoke(testObject);
                    statistics.logSuccessfulRun(method);
                }
            } catch (IllegalArgumentException e) {
                statistics.logFailedRun(method, e.getMessage());
                System.err.format("metoda %s - nieodpowiednia ilość argumentów (%s) %n", method, e.getMessage());
            } catch (IllegalAccessException e) {
                statistics.logFailedRun(method, e.getMessage());
                System.err.format("metoda %s - dostęp zabroniony? %s przyczyna: %s %n", method, e.getMessage(), e.getCause());
            } catch (InvocationTargetException e) {
                statistics.logFailedRun(method, e.getMessage());
                System.err.format("metoda %s - wywołanie nieudane z racji na %s przyczyna: %s %n", method, e.getMessage(), e.getCause());
            }
        }
    }
}

