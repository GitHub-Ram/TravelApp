package com.ixigo.travelapp.utill;

/**
 * Created by F49558B on 4/9/2017.
 */

public class TripPlanStep1 {
    private AutocompletingCity source;
    private AutocompletingCity destination;
    private String fromDate;
    private String returnDate;
    private String tripName;

    public AutocompletingCity getSource() {
        return source;
    }

    public void setSource(AutocompletingCity source) {
        this.source = source;
    }

    public AutocompletingCity getDestination() {
        return destination;
    }

    public void setDestination(AutocompletingCity destination) {
        this.destination = destination;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }
}
