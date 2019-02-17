package pl.kk.tester;

class TesterStatistics {

    private int successCount = 0;

    void addSuccessfulRun() {
        successCount++;
    }

    int methodsRan() {
        return successCount;
    }
}
