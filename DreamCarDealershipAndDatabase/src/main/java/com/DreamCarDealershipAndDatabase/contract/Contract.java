package com.DreamCarDealershipAndDatabase.contract;

import com.DreamCarDealershipAndDatabase.model.Vehicle;

public abstract class Contract {
    protected String date;
    protected String customerName;
    protected String customerEmail;
    protected Vehicle vehicleSold;

    public Contract(String date, String customerName, String customerEmail, Vehicle vehicleSold) {
        this.date = date;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.vehicleSold = vehicleSold;
    }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getCustomerEmail() { return customerEmail; }
    public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }

    public Vehicle getVehicleSold() { return vehicleSold; }

    public abstract double getTotalPrice();
    public abstract double getMonthlyPayment();
}
