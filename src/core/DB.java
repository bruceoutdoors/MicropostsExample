package core;

import app.Transaction;
import java.sql.SQLException;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.flywaydb.core.Flyway;

/**
 *
 * @author Lee Zhen Yong (bruceoutdoors@gmail.com)
 */
public class DB {

    private static final String PERSISTENCE_UNIT_NAME = "MicropostsExamplePU";
    private static final String DB_URL = "jdbc:derby:database;create=true";
    private static final String MIGRATION_DIR = "db.migrations";

    private static DB instance = null;
    
    private final EntityManager em;
    
    private DB() throws SQLException {
        migrateDb();
        em = Persistence
                .createEntityManagerFactory(PERSISTENCE_UNIT_NAME)
                .createEntityManager();
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

    public Query createQuery(String q) {
        return em.createQuery(q);
    }

    public Query createNamedQuery(String q) {
        return em.createNamedQuery(q);
    }

    public void execTransaction(Transaction t) throws PersistenceException {
        em.getTransaction().begin();
        t.execute();
        em.getTransaction().commit();
    }
    
    public void persist(Object entity) throws PersistenceException {
        execTransaction(()->{ em.persist(entity); });
    }
    
    public void remove(Object entity) throws PersistenceException {
        execTransaction(()->{ em.remove(entity); });
    }

    private void migrateDb() {
        Flyway flyway = new Flyway();
        flyway.setDataSource(DB_URL, null, null);
        flyway.setLocations(MIGRATION_DIR);
        flyway.migrate();
    }
}
