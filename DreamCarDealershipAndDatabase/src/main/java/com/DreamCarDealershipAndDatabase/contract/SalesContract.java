package com.DreamCarDealershipAndDatabase.contract;

import com.DreamCarDealershipAndDatabase.model.Vehicle;

public class SalesContract extends Contract {
    private boolean finance;

    public SalesContract(String date, String customerName, String customerEmail, Vehicle vehicleSold, boolean finance) {
        super(date, customerName, customerEmail, vehicleSold);
        this.finance = finance;
    }

    public boolean isFinance() { return finance; }

    @Override
    public double getTotalPrice() {
        double salesTax = vehicleSold.getPrice() * 0.05;
        double recordingFee = 100.00;
        double processingFee = vehicleSold.getPrice() < 10000 ? 295.00 : 495.00;
        return vehicleSold.getPrice() + salesTax + recordingFee + processingFee;
    }

    @Override
    public double getMonthlyPayment() {
        if (!finance) return 0.00;
        double loanAmount = getTotalPrice();
        int months = vehicleSold.getPrice() >= 10000 ? 48 : 24;
        double interestRate = vehicleSold.getPrice() >= 10000 ? 0.0425 : 0.0525;
        return (loanAmount * (1 + interestRate)) / months;
    }
}

