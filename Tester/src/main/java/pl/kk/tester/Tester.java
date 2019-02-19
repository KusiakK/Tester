package pl.kk.tester;


import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Tester {

    private static final TesterStatistics statistics = new TesterStatistics();

    public void process(Class<?> testedClass, Class<? extends Annotation> annotation) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        Constructor<?> constructor = testedClass.getConstructor();
        Object testObject = constructor.newInstance(new Object[] { });

        Method[] methods = testedClass.getDeclaredMethods();

        for (Method method : methods) {
            try {
                if (method.isAnnotationPresent(annotation)) {
                    method.invoke(testObject);
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

