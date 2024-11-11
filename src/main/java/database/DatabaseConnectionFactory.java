package database;

public class DatabaseConnectionFactory {
    private static final String SCHEMA = "library";
    private static final String TEST_SCHEMA = "test_library";

    public static JDBCConnectionWrapper getConnectionWrapper(boolean test){
        if(test){
            return new JDBCConnectionWrapper(TEST_SCHEMA);
        } else {
            return new JDBCConnectionWrapper(SCHEMA);
        }
    }
}
