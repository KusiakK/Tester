package pl.kk.tester;

import pl.kk.annotations.ScanForRun;
import pl.kk.tester.source_file.JavaSourceFile;
import pl.kk.tester.source_file.JavaSourceFiles;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

@SupportedAnnotationTypes("pl.kk.annotations.Run")
@SupportedSourceVersion(SourceVersion.RELEASE_11)
public class TesterProcessor extends AbstractProcessor {

    private String fqClassName = null;
    private List<String> fqClassNames = new LinkedList<>();

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
            }
        }

        if (fqClassName == null) return true;

        Properties properties = new Properties();
        URL url = this.getClass().getClassLoader().getResource("velocity.properties");
        try (final InputStream inStream = url.openStream()) {
            properties.load(inStream);
        } catch (IOException e) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE,
                    String.format("Cannot read configuration file %s - cause: %s %n", url.toString(), e.getMessage()));
        }

        JavaSourceFile sourceFile = JavaSourceFiles.createMainJavaSourceFile(properties, "testerinfo.vm");

        sourceFile.put("fqClassNames", fqClassNames);

        try {
            JavaFileObject jfo = processingEnv.getFiler().createSourceFile("App");
            processingEnv.getMessager().printMessage(
                    Diagnostic.Kind.NOTE,
                    "creating source file: " + jfo.toUri());

            Writer writer = jfo.openWriter();

            sourceFile.mergeAndWrite(writer);

            writer.close();
        } catch (IOException e) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE,
                    String.format("Cannot create source file %s - case: %s %n", url.toString(), e.getMessage()));
        }

        return false;
    }
}