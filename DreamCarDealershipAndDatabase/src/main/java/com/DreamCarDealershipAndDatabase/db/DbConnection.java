package com.DreamCarDealershipAndDatabase.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

    private static final String URL =
            "jdbc:sqlserver://skills4it.database.windows.net:1433;" +  // Server address and port
                    "database=Courses;" +                                    // Target database name
                    "user=user418;" +                            // Username for authentication
                    "password=YearupSecure2025!;" +                           // Password (keep this secure!)
                    "encrypt=true;" +                                        // Encrypt connection
                    "trustServerCertificate=false;" +                        // Do not trust self-signed certs
                    "loginTimeout=30;";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}
