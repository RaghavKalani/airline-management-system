package com.airline.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Flight {
    private int flightId;
    private String flightNumber;
    private int airlineId;
    private int originAirportId;
    private int destinationAirportId;
    private int totalSeats;
    private BigDecimal basePrice;
    private FlightStatus status;
    private LocalDateTime createdAt;

    // Additional fields for joined data
    private String airlineName;
    private String originCity;
    private String destinationCity;
    private String originCode;
    private String destinationCode;

    public enum FlightStatus {
        ACTIVE, CANCELLED, COMPLETED
    }

    // Constructors
    public Flight() {}

    public Flight(int flightId, String flightNumber, int airlineId, int originAirportId,
                  int destinationAirportId, int totalSeats, BigDecimal basePrice,
                  FlightStatus status, LocalDateTime createdAt) {
        this.flightId = flightId;
        this.flightNumber = flightNumber;
        this.airlineId = airlineId;
        this.originAirportId = originAirportId;
        this.destinationAirportId = destinationAirportId;
        this.totalSeats = totalSeats;
        this.basePrice = basePrice;
        this.status = status;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public int getFlightId() { return flightId; }
    public void setFlightId(int flightId) { this.flightId = flightId; }

    public String getFlightNumber() { return flightNumber; }
    public void setFlightNumber(String flightNumber) { this.flightNumber = flightNumber; }

    public int getAirlineId() { return airlineId; }
    public void setAirlineId(int airlineId) { this.airlineId = airlineId; }

    public int getOriginAirportId() { return originAirportId; }
    public void setOriginAirportId(int originAirportId) { this.originAirportId = originAirportId; }

    public int getDestinationAirportId() { return destinationAirportId; }
    public void setDestinationAirportId(int destinationAirportId) { this.destinationAirportId = destinationAirportId; }

    public int getTotalSeats() { return totalSeats; }
    public void setTotalSeats(int totalSeats) { this.totalSeats = totalSeats; }

    public BigDecimal getBasePrice() { return basePrice; }
    public void setBasePrice(BigDecimal basePrice) { this.basePrice = basePrice; }

    public FlightStatus getStatus() { return status; }
    public void setStatus(FlightStatus status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public String getAirlineName() { return airlineName; }
    public void setAirlineName(String airlineName) { this.airlineName = airlineName; }

    public String getOriginCity() { return originCity; }
    public void setOriginCity(String originCity) { this.originCity = originCity; }

    public String getDestinationCity() { return destinationCity; }
    public void setDestinationCity(String destinationCity) { this.destinationCity = destinationCity; }

    public String getOriginCode() { return originCode; }
    public void setOriginCode(String originCode) { this.originCode = originCode; }

    public String getDestinationCode() { return destinationCode; }
    public void setDestinationCode(String destinationCode) { this.destinationCode = destinationCode; }

    @Override
    public String toString() {
        return flightNumber + " (" + originCity + " → " + destinationCity + ")";
    }
}
