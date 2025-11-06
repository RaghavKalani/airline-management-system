package com.airline.model;

import java.time.LocalDateTime;

public class Airport {
    private int airportId;
    private String airportCode;
    private String airportName;
    private String city;
    private String country;
    private LocalDateTime createdAt;

    // Constructors
    public Airport() {}

    public Airport(int airportId, String airportCode, String airportName, 
                   String city, String country, LocalDateTime createdAt) {
        this.airportId = airportId;
        this.airportCode = airportCode;
        this.airportName = airportName;
        this.city = city;
        this.country = country;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public int getAirportId() { return airportId; }
    public void setAirportId(int airportId) { this.airportId = airportId; }

    public String getAirportCode() { return airportCode; }
    public void setAirportCode(String airportCode) { this.airportCode = airportCode; }

    public String getAirportName() { return airportName; }
    public void setAirportName(String airportName) { this.airportName = airportName; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return airportCode + " - " + city;
    }
}
