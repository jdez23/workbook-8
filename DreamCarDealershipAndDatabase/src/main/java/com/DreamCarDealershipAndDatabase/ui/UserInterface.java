package com.DreamCarDealershipAndDatabase.ui;
import com.DreamCarDealershipAndDatabase.contract.*;
import com.DreamCarDealershipAndDatabase.data.*;
import com.DreamCarDealershipAndDatabase.model.Vehicle;

import java.util.List;
import java.util.Scanner;

public class UserInterface {
    private Scanner scanner = new Scanner(System.in);
    private VehicleDao vehicleDao = new VehicleDao();

    public void display() {
        while (true) {
            showMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> processFindByPriceRange();
                case "2" -> processFindByMakeModel();
                case "3" -> processFindByYearRange();
                case "4" -> processFindByColor();
                case "5" -> processFindByMileageRange();
                case "6" -> processFindByType();
                case "7" -> processAllVehiclesRequest();
                case "8" -> processAddVehicleRequest();
                case "9" -> processRemoveVehicleRequest();
                case "10" -> processContractCreation();
                case "99" -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void showMenu() {
        System.out.println("\nPlease select an option:");
        System.out.println("  1 - Find vehicles by price range");
        System.out.println("  2 - Find vehicles by make/model");
        System.out.println("  3 - Find vehicles by year range");
        System.out.println("  4 - Find vehicles by color");
        System.out.println("  5 - Find vehicles by mileage range");
        System.out.println("  6 - Find vehicles by type (car, truck, SUV, van)");
        System.out.println("  7 - List ALL vehicles");
        System.out.println("  8 - Add a vehicle");
        System.out.println("  9 - Remove a vehicle");
        System.out.println(" 10 - Create a contract for sale or lease");
        System.out.println(" 99 - Quit");
        System.out.print("> ");
    }

    private void displayVehicles(List<Vehicle> vehicles) {
        if (vehicles == null || vehicles.isEmpty()) {
            System.out.println("  (no matching vehicles)");
            return;
        }
        for (Vehicle v : vehicles) {
            System.out.println("  VIN: " + v.getVin() +
                    " | " + v.getYear() + " " + v.getMake() + " " + v.getModel() +
                    " | Type: " + v.getVehicleType() +
                    " | Color: " + v.getColor() +
                    " | Miles: " + v.getOdometer() +
                    " | Price: $" + v.getPrice());
        }
    }

    private void processAllVehiclesRequest() {
        List<Vehicle> vehicles = vehicleDao.getVehiclesByPrice(0, Integer.MAX_VALUE);
        displayVehicles(vehicles);
    }

    private void processFindByPriceRange() {
        System.out.print("Enter min price: ");
        int min = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter max price: ");
        int max = Integer.parseInt(scanner.nextLine());

        List<Vehicle> vehicles = vehicleDao.getVehiclesByPrice(min, max);
        displayVehicles(vehicles);
    }

    private void processFindByMakeModel() {
        System.out.print("Enter make: ");
        String make = scanner.nextLine();
        System.out.print("Enter model: ");
        String model = scanner.nextLine();

        List<Vehicle> vehicles = vehicleDao.getVehiclesByMakeModel(make, model);
        displayVehicles(vehicles);
    }

    private void processFindByYearRange() {
        System.out.print("Enter min year: ");
        int min = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter max year: ");
        int max = Integer.parseInt(scanner.nextLine());

        List<Vehicle> vehicles = vehicleDao.getVehiclesByYear(min, max);
        displayVehicles(vehicles);
    }

    private void processFindByColor() {
        System.out.print("Enter color: ");
        String color = scanner.nextLine();

        List<Vehicle> vehicles = vehicleDao.getVehiclesByColor(color);
        displayVehicles(vehicles);
    }

    private void processFindByMileageRange() {
        System.out.print("Enter min mileage: ");
        int min = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter max mileage: ");
        int max = Integer.parseInt(scanner.nextLine());

        List<Vehicle> vehicles = vehicleDao.getVehiclesByMileage(min, max);
        displayVehicles(vehicles);
    }

    private void processFindByType() {
        System.out.print("Enter vehicle type: ");
        String type = scanner.nextLine();

        List<Vehicle> vehicles = vehicleDao.getVehiclesByType(type);
        displayVehicles(vehicles);
    }

    private void processAddVehicleRequest() {
        System.out.print("VIN: ");
        int vin = Integer.parseInt(scanner.nextLine());
        System.out.print("Year: ");
        int year = Integer.parseInt(scanner.nextLine());
        System.out.print("Make: ");
        String make = scanner.nextLine();
        System.out.print("Model: ");
        String model = scanner.nextLine();
        System.out.print("Type: ");
        String type = scanner.nextLine();
        System.out.print("Color: ");
        String color = scanner.nextLine();
        System.out.print("Odometer: ");
        int odometer = Integer.parseInt(scanner.nextLine());
        System.out.print("Price: ");
        double price = Double.parseDouble(scanner.nextLine());

        Vehicle v = new Vehicle(vin, year, make, model, type, color, odometer, price);
        vehicleDao.addVehicle(v);
        System.out.println("Vehicle added.");
    }

    private void processRemoveVehicleRequest() {
        System.out.print("Enter VIN of vehicle to remove: ");
        int vin = Integer.parseInt(scanner.nextLine());

        vehicleDao.removeVehicle(vin);
        System.out.println("Vehicle removed.");
    }

    private void processContractCreation() {
        System.out.print("Enter VIN of vehicle to purchase or lease: ");
        int vin = Integer.parseInt(scanner.nextLine());

        List<Vehicle> all = vehicleDao.getVehiclesByPrice(0, Integer.MAX_VALUE);
        Vehicle selected = all.stream()
                .filter(v -> v.getVin() == vin)
                .findFirst()
                .orElse(null);

        if (selected == null) {
            System.out.println("Vehicle not found.");
            return;
        }

        System.out.print("Enter date (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        System.out.print("Customer name: ");
        String name = scanner.nextLine();
        System.out.print("Customer email: ");
        String email = scanner.nextLine();
        System.out.print("Sale or Lease? (S/L): ");
        String type = scanner.nextLine().trim().toUpperCase();

        if (type.equals("S")) {
            System.out.print("Finance? (yes/no): ");
            boolean finance = scanner.nextLine().equalsIgnoreCase("yes");

            SalesContract contract = new SalesContract(date, name, email, selected, finance);
            new SalesDao().save(contract);
        } else {
            LeaseContract contract = new LeaseContract(date, name, email, selected);
            new LeaseDao().save(contract);
        }

        vehicleDao.removeVehicle(vin);
        System.out.println("Contract saved and vehicle removed from inventory.");
    }
}
