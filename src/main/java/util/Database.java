package util;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    public static Connection getConnection() throws SQLException {
        String databaseUrl = System.getenv("DATABASE_URL");

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
        try {
            URI uri = new URI(databaseUrl);
            String host = uri.getHost();
            int port = uri.getPort();
            if (port > 0) {
                host += ":" + port;
            }
            String db = uri.getPath();
            String username = uri.getUserInfo();
            String password = null;
            int idx = username.indexOf(":");
            if (idx != -1) {
                password = username.substring(idx + 1);
                username = username.substring(0, idx);
            }
            Connection con = DriverManager.getConnection("jdbc:postgresql://" + host + db, username, password);
            return con;
        } catch (URISyntaxException e) {
            throw new IllegalStateException(e);
        }

    }
}
