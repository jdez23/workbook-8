package com.DreamCarDealershipAndDatabase.data;
import com.DreamCarDealershipAndDatabase.model.Vehicle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

public class DealershipFileManager {

    private static final File INVENTORY_FILE = new File("src/main/resources/inventory.csv");

    public static Dealership getDealership() {
        Dealership dealership = new Dealership("Dream Car Dealership", "123 Magic St", "000-000-0000");

        try (BufferedReader reader = new BufferedReader(new FileReader(INVENTORY_FILE))) {
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(Pattern.quote("|"));
                if (tokens.length != 8) {
                    System.out.println("Error in CSV: " + line);
                    continue;
                }

                Vehicle vehicle = new Vehicle();
                vehicle.setVin(Integer.parseInt(tokens[0]));
                vehicle.setYear(Integer.parseInt(tokens[1]));
                vehicle.setMake(tokens[2]);
                vehicle.setModel(tokens[3]);
                vehicle.setVehicleType(tokens[4]);
                vehicle.setColor(tokens[5]);
                vehicle.setOdometer(Integer.parseInt(tokens[6]));
                vehicle.setPrice(Double.parseDouble(tokens[7]));

                dealership.addVehicle(vehicle);
            }

        } catch (IOException e) {
            System.out.println("Error loading inventory: " + e.getMessage());
        }

        return dealership;
    }

    public static void saveDealership(Dealership dealership) {
    }
}
