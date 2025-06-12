package com.DreamCarDealershipAndDatabase.data;

import com.DreamCarDealershipAndDatabase.contract.LeaseContract;
import com.DreamCarDealershipAndDatabase.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class LeaseDao {
    public void save(LeaseContract contract) {
        String sql = """
            INSERT INTO JDEZ.lease_contracts (date, name, email, vin, expected_ending_value, lease_fee, total_price, monthly_fee)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, contract.getDate());
            stmt.setString(2, contract.getCustomerName());
            stmt.setString(3, contract.getCustomerEmail());
            stmt.setInt(4, contract.getVehicleSold().getVin());
            stmt.setDouble(5, contract.getVehicleSold().getPrice() * 0.5);
            stmt.setDouble(6, contract.getVehicleSold().getPrice() * 0.07);
            stmt.setDouble(7, contract.getTotalPrice());
            stmt.setDouble(8, contract.getMonthlyPayment());
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
