package com.example.railwayreservationproject;

public class TrainSchedule {
    private String trainScheduleID;
    private String trainNumber;
    private String origin;
    private String destination;
    private String departureDate;
    private String departureTime;
    private String arrivalDate;
    private String arrivalTime;
    private String duration;
    private double economyPrice;
    private double businessPrice;
    private int economySeatsAvailable;
    private int businessSeatsAvailable;

    public TrainSchedule(String trainNumber, String origin, String destination, String departureDate, String departureTime, String arrivalDate,
                         String arrivalTime,  String duration, double economyPrice, double businessPrice, int economySeatsAvailable, int businessSeatsAvailable) {
        this.trainNumber = trainNumber;
        this.origin = origin;
        this.destination = destination;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.arrivalDate = arrivalDate;
        this.arrivalTime = arrivalTime;
        this.economySeatsAvailable = economySeatsAvailable;
        this.businessSeatsAvailable = businessSeatsAvailable;
        this.duration = duration;
        this.economyPrice = economyPrice;
        this.businessPrice = businessPrice;
    }

    public TrainSchedule(String trainScheduleID,String trainNumber, String origin, String destination, String departureDate, String departureTime, String arrivalDate,
                         String arrivalTime,  String duration, double economyPrice, double businessPrice, int economySeatsAvailable, int businessSeatsAvailable) {
        this.trainScheduleID=trainScheduleID;
        this.trainNumber = trainNumber;
        this.origin = origin;
        this.destination = destination;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.arrivalDate = arrivalDate;
        this.arrivalTime = arrivalTime;
        this.economySeatsAvailable = economySeatsAvailable;
        this.businessSeatsAvailable = businessSeatsAvailable;
        this.duration = duration;
        this.economyPrice = economyPrice;
        this.businessPrice = businessPrice;
    }

    // getters and setters omitted for brevity


    public String getTrainScheduleID() {
        return trainScheduleID;
    }

    public void setTrainScheduleID(String trainScheduleID) {
        this.trainScheduleID = trainScheduleID;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getEconomySeatsAvailable() {
        return economySeatsAvailable;
    }

    public void setEconomySeatsAvailable(int economySeatsAvailable) {
        this.economySeatsAvailable = economySeatsAvailable;
    }

    public int getBusinessSeatsAvailable() {
        return businessSeatsAvailable;
    }

    public void setBusinessSeatsAvailable(int businessSeatsAvailable) {
        this.businessSeatsAvailable = businessSeatsAvailable;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
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