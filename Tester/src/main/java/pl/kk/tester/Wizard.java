package pl.kk.tester;

import org.atteo.classindex.ClassIndex;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

class Wizard {

    Iterable<Class<?>> getAnnotatedClasses(Class<? extends Annotation> annotation) {
        return ClassIndex.getAnnotated(annotation);
    }

    Map<Class<?>, List<Method>> getAnnotatedMethods(Iterable<Class<?>> annotatedClasses, Class<? extends Annotation> annotation) {
        Map<Class<?>, List<Method>> methodsForClass = new HashMap<>();
        for (Class<?> klass : annotatedClasses) {
            Method[] classMethods = klass.getMethods();
            methodsForClass.put(klass, new LinkedList<>());
            for (Method method : classMethods) {
                if (method.isAnnotationPresent(annotation)) methodsForClass.get(klass).add(method);
            }
        }
        return methodsForClass;
    }
}
