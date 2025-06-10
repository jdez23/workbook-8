package com.pluralsight;

import java.sql.*;
import java.util.Scanner;

public class Main {

    private static final String DB_URL =
            "jdbc:sqlserver://skills4it.database.windows.net:1433;" +  // Server address and port
                    "database=Courses;" +                                    // Target database name
                    "user=gtareader@skills4it;" +                            // Username for authentication
                    "password=StrongPass!2025;" +                           // Password (keep this secure!)
                    "encrypt=true;" +                                        // Encrypt connection
                    "trustServerCertificate=false;" +                        // Do not trust self-signed certs
                    "loginTimeout=30;";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }


    public static void main(String[] args) {

        Scanner inputScanner = new Scanner(System.in);
        // Scanner reads integers from System.in for menu selection

        while (true) {  // Infinite loop: keeps showing menu until user exits
            System.out.println("\n=== Bay City SQL CLI ===");  // Print blank line + header
            System.out.println("1. Suspect Scanner (WHERE)");
            System.out.println("2. Vehicle Watchlist (JOIN + WHERE)");
            System.out.println("3. Reward Tracker (GROUP BY + SUM + ORDER BY)");
            System.out.println("4. Elite Agent Filter (GROUP BY + HAVING)");
            System.out.println("5. Search Person");
            System.out.println("6. Search Vehicle");
            System.out.println("7. Search Vehicles A Person Owns");
            System.out.println("8. Find AVG Mission Payout");
            System.out.println("9. Find Inactive Agents");
            System.out.println("10. Find Top Earning Criminals");
            System.out.println("0. Exit");
            System.out.print("Select mission: ");  // Prompt without newline

            int choice = inputScanner.nextInt();  // Read user's menu choice

            // Decide which method to call based on user input
            switch (choice) {
                case 1 -> mission1();           // Call method 1
                case 2 -> mission2();         // Call method 2
                case 3 -> mission3();            // Call method 3
                case 0 -> System.exit(0);                // Exit program
                default -> System.out.println("Invalid choice."); // Handle bad input
            }
        }
    }

    // MISSION 1: Suspect Scanner
    public static void mission1() {
        String sql = "SELECT Name, Alias, WantedLevel FROM Citizens WHERE WantedLevel >= 2";

        try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.printf("Name: %s, Alias: %s, Wanted Level: %d%n",
                        rs.getString("Name"),
                        rs.getString("Alias"),
                        rs.getInt("WantedLevel"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // MISSION 2: Vehicle Watchlist
    public static void mission2() {
        String sql = """
                SELECT c.Name, v.Type, v.Brand
                FROM Citizens c
                JOIN Vehicles v ON c.CitizenID = v.OwnerID
                WHERE v.IsStolen = 1
                """;

        try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.printf("Name: %s, Type: %s, Brand: %s%n",
                        rs.getString("Name"),
                        rs.getString("Type"),
                        rs.getString("Brand"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void mission3() {
        String sql = """
                SELECT c.Name, SUM(a.Reward) AS TotalEarnings
                FROM Citizens c
                JOIN Assignments a ON c.CitizenID = a.CitizenID
                GROUP BY c.Name
                ORDER BY TotalEarnings DESC
                """;

        try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.printf("Name: %s, Total Earnings: $%d%n",
                        rs.getString("Name"),
                        rs.getInt("TotalEarnings"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}