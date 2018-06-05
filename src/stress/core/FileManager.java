package stress.core;

public class FileManager {

    StressManager parent;

    public FileManager(StressManager parent) {
        this.parent = parent;
    }

    public Boolean exportFile(String filename) {
        return false;
    }

    public Boolean importFile(String filename) {
        return false;
    }
}
