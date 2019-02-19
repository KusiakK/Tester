package pl.kk.tester.source_file;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.util.Properties;

/**
 * {@link JavaSourceFile} helper class, to create instance of specific Java source files.
 *
 * @author blysku
 */
public class JavaSourceFiles {

    /**
     * @param properties for Velocity engine
     * @param velocityTemplateFilePath
     * @return {@link JavaSourceFile}
     */
    public static JavaSourceFile createMainJavaSourceFile(Properties properties, String velocityTemplateFilePath) {
        VelocityEngine velocityEngine = initVelocityEngine(properties);
        return new MainJavaSourceFile(new VelocityContext(), velocityEngine.getTemplate(velocityTemplateFilePath));
    }

    private static VelocityEngine initVelocityEngine(Properties properties) {
        VelocityEngine velocityEngine = new VelocityEngine(properties);
        velocityEngine.init();
        return velocityEngine;
    }
}
