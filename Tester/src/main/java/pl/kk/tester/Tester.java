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
            } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
                statistics.logFailedRun(method, e.getMessage());
            }
        }
    }
}

