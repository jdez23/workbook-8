package com.DreamCarDealershipAndDatabase.data;

import com.DreamCarDealershipAndDatabase.contract.SalesContract;
import com.DreamCarDealershipAndDatabase.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class SalesDao {
    public void save(SalesContract contract) {
        String sql = """
            INSERT INTO JDEZ.sales_contracts (date, name, email, vin, total_price, leased, monthly)
            VALUES (?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, contract.getDate());
            stmt.setString(2, contract.getCustomerName());
            stmt.setString(3, contract.getCustomerEmail());
            stmt.setInt(4, contract.getVehicleSold().getVin());
            stmt.setDouble(5, contract.getTotalPrice());
            stmt.setBoolean(6, contract.isFinance());
            stmt.setDouble(7, contract.getMonthlyPayment());
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
