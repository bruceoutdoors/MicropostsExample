package app;

import java.io.IOException;
import java.sql.SQLException;

/**
 *
 * @author Lee Zhen Yong (bruceoutdoors@gmail.com)
 */
public class Main {

    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("seed")) {
            try {
                core.DB.getInstance().seedDb();
            } catch (IOException | SQLException ex) {
                System.err.println("Error when Attempting to seed database: " + ex.toString());
            }
            return;
        }

        java.awt.EventQueue.invokeLater(() -> {
            new MainWindow().setVisible(true);
        });
    }
}
