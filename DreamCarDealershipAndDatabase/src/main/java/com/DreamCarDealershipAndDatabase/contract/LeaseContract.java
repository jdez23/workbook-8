package com.DreamCarDealershipAndDatabase.contract;

import com.DreamCarDealershipAndDatabase.model.Vehicle;

public class LeaseContract extends Contract {
    public LeaseContract(String date, String customerName, String customerEmail, Vehicle vehicleSold) {
        super(date, customerName, customerEmail, vehicleSold);
    }

    @Override
    public double getTotalPrice() {
        double endingValue = vehicleSold.getPrice() * 0.5;
        double leaseFee = vehicleSold.getPrice() * 0.07;
        return endingValue + leaseFee;
    }

    @Override
    public double getMonthlyPayment() {
        double leaseAmount = getTotalPrice();
        double interestRate = 0.04;
        int months = 36;
        return (leaseAmount * (1 + interestRate)) / months;
    }
}
