package pl.kk.tester;

import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TesterStatistics {

    private final List<Method> methodsSucceed = new ArrayList<>();
    private final Map<Method, String> methodsFailed = new HashMap<>();
    private int countSucceed;
    private int countFailed;


    void logSuccessfulRun(Method method) {
        methodsSucceed.add(method);
        countSucceed++;
    }

    void logFailedRun(Method method, String message) {
        methodsFailed.putIfAbsent(method, message);
        countFailed++;
    }

    public void printStatistics(PrintStream printStream) {
        printSucceed(printStream);
        printFailed(printStream);
    }

    private void printSucceed(PrintStream printStream) {
        for (Method method : methodsSucceed) {
            printStream.printf("Method succeed: %s%n", method);
        }
        printStream.printf("All methods succeed: %d%n", countSucceed);
    }

    private void printFailed(PrintStream printStream) {
        for (Map.Entry<Method,String> entry : methodsFailed.entrySet()) {
            printStream.printf("Method failed: %s; cause: %s%n", entry.getKey().toString(), entry.getValue());
        }
        printStream.printf("All methods failed: %d%n", countFailed);
    }
}
