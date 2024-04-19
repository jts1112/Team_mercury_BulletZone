package edu.unh.cs.cs619.bulletzone.repository;

public class DataRepositoryFactory {
    private static DataRepository instance;

    private DataRepositoryFactory() {
    }

    public static synchronized DataRepository getInstance() {
        if (instance == null) {
            instance = new DataRepository();
        }
        return instance;
    }
}
