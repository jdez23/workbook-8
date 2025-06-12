package com.DreamCarDealershipAndDatabase.data;

import com.DreamCarDealershipAndDatabase.db.DbConnection;
import com.DreamCarDealershipAndDatabase.model.Vehicle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleDao {

    public List<Vehicle> getVehiclesByPrice(int min, int max) {
        return queryVehicles("SELECT * FROM JDEZ.vehicles WHERE price BETWEEN ? AND ?", min, max);
    }

    public List<Vehicle> getVehiclesByMakeModel(String make, String model) {
        return queryVehicles("SELECT * FROM JDEZ.vehicles WHERE make = ? AND model = ?", make, model);
    }

    public List<Vehicle> getVehiclesByYear(int min, int max) {
        return queryVehicles("SELECT * FROM JDEZ.vehicles WHERE year BETWEEN ? AND ?", min, max);
    }

    public List<Vehicle> getVehiclesByMileage(int min, int max) {
        return queryVehicles("SELECT * FROM JDEZ.vehicles WHERE odometer BETWEEN ? AND ?", min, max);
    }

    public List<Vehicle> getVehiclesByColor(String color) {
        return queryVehicles("SELECT * FROM JDEZ.vehicles WHERE color = ?", color);
    }

    public List<Vehicle> getVehiclesByType(String type) {
        return queryVehicles("SELECT * FROM JDEZ.vehicles WHERE vehicle_type = ?", type);
    }

    private List<Vehicle> queryVehicles(String sql, Object... params) {
        List<Vehicle> vehicles = new ArrayList<>();
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                vehicles.add(extractVehicle(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }

    private Vehicle extractVehicle(ResultSet rs) throws SQLException {
        return new Vehicle(
                rs.getInt("vin"),
                rs.getInt("year"),
                rs.getString("make"),
                rs.getString("model"),
                rs.getString("vehicle_type"),
                rs.getString("color"),
                rs.getInt("odometer"),
                rs.getDouble("price")
        );
    }

    public void addVehicle(Vehicle v) {
        String sql = "INSERT INTO JDEZ.vehicles (vin, year, make, model, vehicle_type, color, odometer, price, sold) VALUES (?, ?, ?, ?, ?, ?, ?, ?, 0)";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, v.getVin());
            stmt.setInt(2, v.getYear());
            stmt.setString(3, v.getMake());
            stmt.setString(4, v.getModel());
            stmt.setString(5, v.getVehicleType());
            stmt.setString(6, v.getColor());
            stmt.setInt(7, v.getOdometer());
            stmt.setDouble(8, v.getPrice());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeVehicle(int vin) {
        String sql = "DELETE FROM JDEZ.vehicles WHERE vin = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, vin);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
