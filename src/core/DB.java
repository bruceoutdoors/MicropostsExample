package core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.flywaydb.core.Flyway;

/**
 *
 * @author Lee Zhen Yong (bruceoutdoors@gmail.com)
 */
public class DB {

    private static final String DB_URL = "jdbc:sqlite:database.db";
    private static final String MIGRATION_DIR = "db.migrations";
    private static final String SEED_FILE = "/db/seed.sql";

    private static DB instance = null;

    private final Connection mConnection;

    private DB() throws SQLException {
        migrateDb();

        mConnection = DriverManager.getConnection(DB_URL);

        // enable foreign key support in SQLite:
        executeUpdate("PRAGMA foreign_keys = ON");
    }

    public DB(Connection mConnection) {
        this.mConnection = mConnection;
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

    public void seedDb() throws IOException, SQLException {
        SqlScriptRunner runner = new SqlScriptRunner(mConnection, false, false);
        InputStream in = DB.class.getResourceAsStream(SEED_FILE);
        runner.runScript(new BufferedReader(new InputStreamReader(in)));
    }

    private void migrateDb() {
        Flyway flyway = new Flyway();
        flyway.setDataSource(DB_URL, null, null);
        flyway.setLocations(MIGRATION_DIR);
        flyway.migrate();
    }
    
    public static String getDateString(Date d) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(d);
    }

    public static Date getDate(ResultSet rs, String columnName) {
        // NOTE: Don't use Julian Date. It's not supported here
        final String DATE_FORMATS[] = {
            "yyyy-MM-dd HH:mm:ss.SSS", "yyyy-MM-dd'T'HH:mm:ss.SSS",
            "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd'T'HH:mm:ss",
            "yyyy-MM-dd HH:mm", "yyyy-MM-dd'T'HH:mm",
            "yyyy-MM-dd", "HH:mm:ss.SSS", "HH:mm:ss", "HH:mm"
        };

        for (String format : DATE_FORMATS) {
            try {
                return new SimpleDateFormat(format).parse(rs.getString(columnName));
            } catch (ParseException ex) {} // ignore parse exceptions; try next format
            catch (SQLException ex) {
                Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
                break;
            }
        }

        return null;
    }
}
