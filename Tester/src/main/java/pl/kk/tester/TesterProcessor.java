package pl.kk.tester;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.net.URL;
import java.util.*;

@SupportedAnnotationTypes("pl.kk.annotations.Run")
@SupportedSourceVersion(SourceVersion.RELEASE_11)
public class TesterProcessor extends AbstractProcessor {

    private String fqClassName = null;
    private Map<String, ExecutableElement> methods = new HashMap<>();
    private List<String> fqClassNames = new LinkedList<>();


    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element e : roundEnv.getElementsAnnotatedWith(ScanForRun.class)) {

            if (e.getKind() == ElementKind.CLASS) {

                TypeElement classElement = (TypeElement) e;

                processingEnv.getMessager().printMessage(
                        Diagnostic.Kind.NOTE,
                        "annotated class: " + classElement.getQualifiedName(), e);

                fqClassName = classElement.getQualifiedName().toString();

                fqClassNames.add(fqClassName);
            } else if (e.getKind() == ElementKind.METHOD) {

                ExecutableElement exeElement = (ExecutableElement) e;

                processingEnv.getMessager().printMessage(
                        Diagnostic.Kind.NOTE,
                        "annotated method: " + exeElement.getSimpleName(), e);

                methods.put(exeElement.getSimpleName().toString(), exeElement);
            }
        }

        if (fqClassName == null) return true;

        Properties props = new Properties();
        URL url = this.getClass().getClassLoader().getResource("velocity.properties");
        try (final InputStream inStream = url.openStream()) {
            props.load(inStream);
        } catch (IOException e) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE,
                    String.format("Nie można wczytać pliku konfiguracyjnego %s - z powodu %s %n", url.toString(), e.getMessage()));
        }

        VelocityEngine ve = new VelocityEngine(props);
        ve.init();

        VelocityContext vc = new VelocityContext();

        vc.put("methods", methods);
        vc.put("fqClassNames", fqClassNames);

        Template vt = ve.getTemplate("testerinfo.vm");

        try {
            JavaFileObject jfo = processingEnv.getFiler().createSourceFile("App");
            processingEnv.getMessager().printMessage(
                    Diagnostic.Kind.NOTE,
                    "creating source file: " + jfo.toUri());

            Writer writer = jfo.openWriter();

            processingEnv.getMessager().printMessage(
                    Diagnostic.Kind.NOTE,
                    "applying velocity template: " + vt.getName());

            vt.merge(vc, writer);

            writer.close();
        } catch (IOException e) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE,
                    String.format("Nie można utworzyć pliku %s - z powodu %s %n", url.toString(), e.getMessage()));
        }

        return false;
    }
}