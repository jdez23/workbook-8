package com.DreamCarDealershipAndDatabase.contract;

import com.DreamCarDealershipAndDatabase.model.Vehicle;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ContractFileManager {

    private static final String CONTRACTS_FILE = "src/main/resources/contracts.csv";

    public void saveContract(Contract contract) {
        try (PrintWriter out = new PrintWriter(new FileWriter(CONTRACTS_FILE, true))) {
            Vehicle v = contract.getVehicleSold();

            if (contract instanceof SalesContract sc) {
                out.printf("SALE|%s|%s|%s|%d|%d|%s|%s|%s|%s|%d|%.2f|%.2f|%.2f|%.2f|%.2f|%s|%.2f\n",
                        sc.getDate(),
                        sc.getCustomerName(),
                        sc.getCustomerEmail(),
                        v.getVin(), v.getYear(), v.getMake(), v.getModel(),
                        v.getVehicleType(), v.getColor(), v.getOdometer(), v.getPrice(),
                        v.getPrice() * 0.05, // tax
                        100.00,
                        v.getPrice() < 10000 ? 295.00 : 495.00,
                        sc.getTotalPrice(),
                        sc.isFinance() ? sc.getMonthlyPayment() : 0.00,
                        sc.isFinance() ? "YES" : "NO",
                        sc.getMonthlyPayment()
                );
            } else if (contract instanceof LeaseContract lc) {
                out.printf("LEASE|%s|%s|%s|%d|%d|%s|%s|%s|%s|%d|%.2f|%.2f|%.2f|%.2f\n",
                        lc.getDate(),
                        lc.getCustomerName(),
                        lc.getCustomerEmail(),
                        v.getVin(), v.getYear(), v.getMake(), v.getModel(),
                        v.getVehicleType(), v.getColor(), v.getOdometer(), v.getPrice(),
                        v.getPrice() * 0.5,
                        v.getPrice() * 0.07,
                        lc.getTotalPrice(),
                        lc.getMonthlyPayment()
                );
            }

        } catch (IOException e) {
            System.out.println("Error writing contract: " + e.getMessage());
        }
    }
}
