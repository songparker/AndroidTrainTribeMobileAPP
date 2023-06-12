package com.example.railwayreservationproject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Schedule {
    private String scheduleID;
    private Train train;
    private String departureDate;
    private String departureTime;
    private String arrivalDate;
    private String arrivalTime;
    private int ecoAvailability;
    private int busiAvailability;
    private String duration;
    private String trainNum; // foreign key to Train class

    public Schedule() {
        // Default constructor
    }

    public Schedule(String trainNum, String departureDate, String departureTime,
                    String arrivalDate, String arrivalTime, int ecoAvailability, int busiAvailability) {
        this.trainNum = trainNum;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.arrivalDate = arrivalDate;
        this.arrivalTime = arrivalTime;
        this.ecoAvailability = ecoAvailability;
        this.busiAvailability = busiAvailability;
        this.duration = calculateDuration(departureTime, arrivalTime, departureDate, arrivalDate);
    }

    // Getters and setters for all attributes
    public String getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(String scheduleID) {
        this.scheduleID = scheduleID;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public String getTrainNum() {
        return trainNum;
    }

    public void setTrainNum(String trainNum) {
        this.trainNum = trainNum;
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

    public int getEcoAvailability() {
        return ecoAvailability;
    }

    public void setEcoAvailability(int ecoAvailability) {
        this.ecoAvailability = ecoAvailability;
    }

    public int getBusiAvailability() {
        return busiAvailability;
    }

    public void setBusiAvailability(int busiAvailability) {
        this.busiAvailability = busiAvailability;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    private String calculateDuration(String departureTime, String arrivalTime, String departureDate, String arrivalDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd-MM-yyyy");
        try {
            Date departureDateTime = sdf.parse(departureTime + " " + departureDate);
            Date arrivalDateTime = sdf.parse(arrivalTime + " " + arrivalDate);
            if (arrivalDateTime.before(departureDateTime)) {
                // If arrival date is before departure date, add 24 hours to the arrival date
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(arrivalDateTime);
                calendar.add(Calendar.DATE, 1);
                arrivalDateTime = calendar.getTime();
            }
            long durationInMillis = arrivalDateTime.getTime() - departureDateTime.getTime();
            int durationInHours = (int) TimeUnit.MILLISECONDS.toHours(durationInMillis);
            int durationInMinutes = (int) TimeUnit.MILLISECONDS.toMinutes(durationInMillis) % 60;
            return durationInHours + " hrs " + durationInMinutes + " m";
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

}
