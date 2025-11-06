package com.airline.model;

import java.time.LocalDateTime;

public class Airline {
    private int airlineId;
    private String airlineCode;
    private String airlineName;
    private String country;
    private LocalDateTime createdAt;

    // Constructors
    public Airline() {}

    public Airline(int airlineId, String airlineCode, String airlineName, String country, LocalDateTime createdAt) {
        this.airlineId = airlineId;
        this.airlineCode = airlineCode;
        this.airlineName = airlineName;
        this.country = country;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public int getAirlineId() { return airlineId; }
    public void setAirlineId(int airlineId) { this.airlineId = airlineId; }

    public String getAirlineCode() { return airlineCode; }
    public void setAirlineCode(String airlineCode) { this.airlineCode = airlineCode; }

    public String getAirlineName() { return airlineName; }
    public void setAirlineName(String airlineName) { this.airlineName = airlineName; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return airlineCode + " - " + airlineName;
    }
}
