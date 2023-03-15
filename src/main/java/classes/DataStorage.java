package classes;

public class DataStorage {

    private static DataStorage INSTANCE;
    public final static String DEFAULT_STORAGE_FILE_PATH = "storage.xml";
    public static String CURRENT_STORAGE_FILE_PATH;
    public static String CURRENT_STORAGE_FILE_NAME;
    public final static String DEFAULT_STORAGE_FILE_NAME = "storage.xml";
    public final static String EMPTY_STORAGE_SAMPLE_FILE_NAME = "empty_collection_sample.xml";
    public final static String EMPTY_STORAGE_SAMPLE_FILE_PATH = "src/main/resources/empty_collection_sample.xml";
    public final static String XML = ".xml";



    public static String getCurrentStorageFilePath() {
        return CURRENT_STORAGE_FILE_PATH;
    }

    public static void setCurrentStorageFilePath(String currentStorageFilePath) {
        CURRENT_STORAGE_FILE_PATH = currentStorageFilePath;
    }

    public static String getCurrentStorageFileName() {
        return CURRENT_STORAGE_FILE_NAME;
    }

    public static void setCurrentStorageFileName(String currentStorageFileName) {
        CURRENT_STORAGE_FILE_NAME = currentStorageFileName;
    }

}
