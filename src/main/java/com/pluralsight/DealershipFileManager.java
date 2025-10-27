package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DealershipFileManager {
    public Dealership getDealership() {
        Dealership dealership;
        try{
            FileReader fileReader = new FileReader("src/main/java/com/pluralsight/data/vehicles.csv");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String dealershipInfo = bufferedReader.readLine();
            if (dealershipInfo == null) {
                System.out.println("No dealership info found.");
                bufferedReader.close();
                return null;
            }

            String trimmedDealerInfo = dealershipInfo.trim();
            if(trimmedDealerInfo.length() == 0) {
                System.out.println("Blank");
                return null;
            }

            String[] dealerInfoParts = trimmedDealerInfo.split("\\|");
            if(dealerInfoParts.length < 3) {
                System.out.println("Dealer format incorrect, please fix. " + trimmedDealerInfo);
                bufferedReader.close();
                return null;
            }
            String name = dealerInfoParts[0].trim();
            String address = dealerInfoParts[1].trim();
            String phone = dealerInfoParts[2].trim();
            dealership = new Dealership(name, address, phone);

            String input;
            while((input = bufferedReader.readLine()) != null) {
                String trimmedInput = input.trim();

                if(trimmedInput.length() == 0) {
                    continue;
                }

                String[] vehicleParts = trimmedInput.split("\\|");
                if(vehicleParts.length < 8) {
                    System.out.println("Bad line, skipping onto next" + trimmedInput);
                    continue;
                }
                String vVin = vehicleParts[0].trim();
                String vYear = vehicleParts[1].trim();
                String vMake = vehicleParts[2].trim();
                String vModel = vehicleParts[3].trim();
                String vType = vehicleParts[4].trim();
                String vColor = vehicleParts[5].trim();
                String vOdometer = vehicleParts[6].trim();
                String vPrice = vehicleParts[7].trim();

                try{
                dealership.addVehicle(new Vehicle(Integer.parseInt(vVin), Integer.parseInt(vYear), vMake, vModel, vType, vColor, Integer.parseInt(vOdometer), Double.parseDouble(vPrice)));
                } catch (NumberFormatException e) {
                    System.out.println("broken line:" + trimmedInput);
                }
            }
            bufferedReader.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return dealership;
    }

    public void saveDealership(Dealership dealership) {

    }
}
