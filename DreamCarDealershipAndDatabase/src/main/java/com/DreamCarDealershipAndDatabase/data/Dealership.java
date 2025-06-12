package com.DreamCarDealershipAndDatabase.data;

import com.DreamCarDealershipAndDatabase.model.Vehicle;
import java.util.ArrayList;
import java.util.List;

public class Dealership {
    private String name;
    private String address;
    private String phone;
    private List<Vehicle> inventory;

    public Dealership(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.inventory = new ArrayList<>();
    }

    public void addVehicle(Vehicle vehicle) {
        inventory.add(vehicle);
    }

    public void removeVehicle(Vehicle vehicle) {
        inventory.remove(vehicle);
    }

    public List<Vehicle> getAllVehicles() {
        return inventory;
    }

    // Search methods stubbed out, return null for now
    public List<Vehicle> getVehiclesByPrice(int min, int max) { return null; }
    public List<Vehicle> getVehiclesByMakeModel(String make, String model) { return null; }
    public List<Vehicle> getVehiclesByYear(int min, int max) { return null; }
    public List<Vehicle> getVehiclesByColor(String color) { return null; }
    public List<Vehicle> getVehiclesByMileage(int min, int max) { return null; }
    public List<Vehicle> getVehiclesByType(String vehicleType) { return null; }
}
