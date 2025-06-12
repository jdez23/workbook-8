package com.DreamCarDealershipAndDatabase.ui;
import com.DreamCarDealershipAndDatabase.contract.*;
import com.DreamCarDealershipAndDatabase.data.*;
import com.DreamCarDealershipAndDatabase.model.Vehicle;

import java.util.List;
import java.util.Scanner;

public class UserInterface {
    private Dealership dealership;
    private Scanner scanner = new Scanner(System.in);

    public void display() {
        init();  // load dealership via file manager

        while (true) {
            showMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    processFindByPriceRange();
                    break;
                case "2":
                    processFindByMakeModel();
                    break;
                case "3":
                    processFindByYearRange();
                    break;
                case "4":
                    processFindByColor();
                    break;
                case "5":
                    processFindByMileageRange();
                    break;
                case "6":
                    processFindByType();
                    break;
                case "7":
                    processAllVehiclesRequest();
                    break;
                case "8":
                    processAddVehicleRequest();
                    break;
                case "9":
                    processRemoveVehicleRequest();
                    break;
                case "10":
                    processContractCreation();
                    break;

                case "0":
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    //  Loads the Dealership from file via DealershipFileManager.
    private void init() {
        this.dealership = DealershipFileManager.getDealership();
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
        System.out.println("  10 - Create a contract for sale or lease");
        System.out.println("  0 - Quit");
        System.out.print("> ");
    }

    private void displayVehicles(List<Vehicle> vehicles) {
        if (vehicles == null || vehicles.isEmpty()) {
            System.out.println("  (no matching vehicles)");
            return;
        }
        for (Vehicle v : vehicles) {
            System.out.println("  " + v);
        }
    }

    private void processAllVehiclesRequest() {
        List<Vehicle> all = dealership.getAllVehicles();
        displayVehicles(all);
    }

    private void processContractCreation() {
        System.out.print("Enter VIN of vehicle to purchase or lease: ");
        int vin = Integer.parseInt(scanner.nextLine());

        Vehicle selected = dealership.getAllVehicles().stream()
                .filter(v -> v.getVin() == vin)
                .findFirst()
                .orElse(null);

        if (selected == null) {
            System.out.println("Vehicle not found.");
            return;
        }

        System.out.print("Enter date (YYYYMMDD): ");
        String date = scanner.nextLine();

        System.out.print("Customer name: ");
        String name = scanner.nextLine();

        System.out.print("Customer email: ");
        String email = scanner.nextLine();

        System.out.print("Sale or Lease? (S/L): ");
        String type = scanner.nextLine().trim().toUpperCase();

        Contract contract;
        if (type.equals("S")) {
            System.out.print("Finance? (yes/no): ");
            boolean finance = scanner.nextLine().equalsIgnoreCase("yes");

            contract = new SalesContract(date, name, email, selected, finance);
        } else {
            contract = new LeaseContract(date, name, email, selected);
        }

        // Save contract
        new ContractFileManager().saveContract(contract);
        dealership.removeVehicle(selected);
        System.out.println("Contract saved and vehicle removed from inventory.");
    }



    private void processFindByPriceRange() {}
    private void processFindByMakeModel()  {}
    private void processFindByYearRange()  {}
    private void processFindByColor()      {}
    private void processFindByMileageRange(){}
    private void processFindByType()       {}
    private void processAddVehicleRequest(){}
    private void processRemoveVehicleRequest(){}
}
