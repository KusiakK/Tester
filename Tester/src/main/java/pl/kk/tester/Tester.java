package pl.kk.tester;

import pl.kk.annotations.Run;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.lang.reflect.Method;
import java.util.Set;

@SupportedAnnotationTypes("pl.kk.annotations.Run")
@SupportedSourceVersion(SourceVersion.RELEASE_11)
public class Tester extends AbstractProcessor {

    private static final TesterStatistics statistics = new TesterStatistics();

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        StringBuilder message = new StringBuilder();

        for (Element elem : roundEnv.getElementsAnnotatedWith(Run.class)) {
            Run implementation = elem.getAnnotation(Run.class);

            message.append("Methods found in " + elem.getSimpleName().toString() + ":\n");

            for (Method method : implementation.getClass().getMethods()) {
                message.append(method.getName() + "\n");
            }

            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, message.toString());
        }
        return false;
    }
}

