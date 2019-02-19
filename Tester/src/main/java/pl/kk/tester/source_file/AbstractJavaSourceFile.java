package pl.kk.tester.source_file;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

import java.io.Writer;

abstract class AbstractJavaSourceFile implements JavaSourceFile {
    private final VelocityContext velocityContext;
    private Template template;

    AbstractJavaSourceFile(VelocityContext velocityContext, Template template) {
        this.velocityContext = velocityContext;
        this.template = template;
    }

    @Override
    public void put(String key, Object value) {
        velocityContext.put(key, value);
    }

    @Override
    public void mergeAndWrite(Writer writer) {
        template.merge(velocityContext, writer);
    }
}
