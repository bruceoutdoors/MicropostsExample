package core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.flywaydb.core.Flyway;

/**
 *
 * @author Lee Zhen Yong (bruceoutdoors@gmail.com)
 */
public class DB {

    private static final String DB_URL = "jdbc:derby:database;create=true";
    private static final String MIGRATION_DIR = "db.migrations";

    private static DB instance = null;

    private final Connection mConnection;

    private DB() throws SQLException {
        migrateDb();

        mConnection = DriverManager.getConnection(DB_URL);
    }

    public static DB getInstance() {
        if (instance == null) {
            try {
                instance = new DB();
            } catch (SQLException ex) {
                System.err.println("Database Error: " + ex.toString());
                System.exit(1);
            }
        }

        return instance;
    }

    public int executeUpdate(String sql) throws SQLException {
        return mConnection.createStatement().executeUpdate(sql);
    }

    public ResultSet executeQuery(String sql) throws SQLException {
        return mConnection.createStatement().executeQuery(sql);
    }

    public PreparedStatement getPreparedStatement(String sql) throws SQLException {
        return mConnection.prepareStatement(sql);
    }

    public boolean hasTable(String tableName) {
        try {
            executeQuery("SELECT count(*) FROM " + tableName);
        } catch (SQLException ex) {
            return false;
        }

        return true;
    }

    private void migrateDb() {
        Flyway flyway = new Flyway();
        flyway.setDataSource(DB_URL, null, null);
        flyway.setLocations(MIGRATION_DIR);
        flyway.migrate();
    }
}
