package com.example.railwayreservationproject;

public class Train {
    private String trainNumber;
    private String sourceStation;
    private String destinationStation;
    private double economyPrice;
    private double businessPrice;

    public Train(String trainNumber, String sourceStation, String destinationStation, double economyPrice, double businessPrice) {
        this.trainNumber = trainNumber;
        this.sourceStation = sourceStation;
        this.destinationStation = destinationStation;
        this.economyPrice = economyPrice;
        this.businessPrice = businessPrice;
    }

    // Getters and setters for all attributes
    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getSourceStation() {
        return sourceStation;
    }

    public void setSourceStation(String sourceStation) {
        this.sourceStation = sourceStation;
    }

    public String getDestinationStation() {
        return destinationStation;
    }

    public void setDestinationStation(String destinationStation) {
        this.destinationStation = destinationStation;
    }

    public double getEconomyPrice() {
        return economyPrice;
    }

    public void setEconomyPrice(double economyPrice) {
        this.economyPrice = economyPrice;
    }

    public double getBusinessPrice() {
        return businessPrice;
    }

    public void setBusinessPrice(double businessPrice) {
        this.businessPrice = businessPrice;
    }
}