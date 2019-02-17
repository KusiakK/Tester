package pl.kk.tester;

import pl.kk.annotations.Run;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class Tester {
    public static void main(String[] args) {
        Tester tester = new Tester();
        Method[] methods = tester.getClass().getDeclaredMethods();

        int methodsRan = 0;
        for (Method method : methods) {
            if (!method.isAnnotationPresent(Run.class)) continue;
            try {
                method.invoke(tester);
                methodsRan++;
            } catch (IllegalArgumentException e) {
                System.err.format("metoda %s - nieodpowiednia ilość argumentów (%s) %n", method, e.getMessage());
            } catch (IllegalAccessException e) {
                System.err.format("metoda %s - dostęp zabroniony? %s przyczyna: %s %n", method, e.getMessage(), e.getCause());
            } catch (InvocationTargetException e) {
                System.err.format("metoda %s - wywołanie nieudane z racji na %s przyczyna: %s %n", method, e.getMessage(), e.getCause());
            }
        }
        System.out.format("Odpalone metody : %d", methodsRan);
    }

    @Run
    public void odpalana() {
        System.out.println("odpalana się odpala");
    }

    @Run
    public void metodka() {
        System.out.println("metodka się odpala");
    }
}
