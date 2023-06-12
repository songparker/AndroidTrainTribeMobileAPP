package com.example.railwayreservationproject;

public class Order {
    private String orderId;
    private Client client;
    private Train train;
    private Schedule schedule;
    private String orderDate;
    private String trainNum;
    private String scheduleID;
    private String clientID; // foreign key to Client class

    private int ecoNumber;
    private int busiNumber;
    private double totalPrice;

    // Constructor
    public Order(String orderId, Client client, Train train, Schedule schedule, String orderDate, int ecoNumber, int busiNumber, double totalPrice) {
        this.orderId = orderId;
        this.client = client;
        this.train = train;
        this.schedule = schedule;
        this.orderDate = orderDate;
        this.ecoNumber = ecoNumber;
        this.busiNumber = busiNumber;
        this.totalPrice = totalPrice;
    }

    // Getters and Setters
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public int getEcoNumber() {
        return ecoNumber;
    }

    public void setEcoNumber(int ecoNumber) {
        this.ecoNumber = ecoNumber;
    }

    public int getBusiNumber() {
        return busiNumber;
    }

    public void setBusiNumber(int busiNumber) {
        this.busiNumber = busiNumber;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getTrainNum() {
        return trainNum;
    }

    public void setTrainNum(String trainNum) {
        this.trainNum = trainNum;
    }

    public String getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(String scheduleID) {
        this.scheduleID = scheduleID;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    // Method to calculate total price based on selected train and seat
    public double calculateTotalPrice() {
        double ecoPrice = train.getEconomyPrice();
        double busiPrice = train.getBusinessPrice();
        double totalPrice = ecoPrice * ecoNumber + busiPrice * busiNumber;
        return totalPrice;
    }

    // Method to update seat availability after booking
    public void updateSeatAvailability() {
        int availableEcoSeats = schedule.getEcoAvailability();
        availableEcoSeats = availableEcoSeats - ecoNumber;
        schedule.setEcoAvailability(availableEcoSeats);

        int availableBusiSeats = schedule.getBusiAvailability();
        availableBusiSeats = availableBusiSeats - busiNumber;
        schedule.setBusiAvailability(availableBusiSeats);
    }


    // Method to generate e-ticket with all relevant information
    public void generateETicket() {
        System.out.println("E-Ticket for Order #" + orderId);
        System.out.println("Client Name: " + client.getFirstName() + " " + client.getLastName());
        System.out.println("Train Number: " + train.getTrainNumber());
        System.out.println("Source Station: " + train.getSourceStation());
        System.out.println("Destination Station: " + train.getDestinationStation());
        System.out.println("Departure Time: " + schedule.getDepartureTime() + " on " + schedule.getDepartureDate());
        System.out.println("Arrival Time: " + schedule.getArrivalTime() + " on " + schedule.getArrivalDate());
        System.out.println("Economy class: " + ecoNumber);
        System.out.println("Business class: " + busiNumber);
        System.out.println("Total Price: $" + totalPrice);
    }
}
