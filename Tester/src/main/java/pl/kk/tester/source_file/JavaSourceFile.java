package pl.kk.tester.source_file;

import java.io.Writer;

public interface JavaSourceFile extends ContextHolder {

    void mergeAndWrite(Writer writer);
}
