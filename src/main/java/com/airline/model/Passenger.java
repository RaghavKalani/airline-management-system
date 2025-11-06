package com.airline.model;

public class Passenger {
    private int passengerId;
    private int bookingId;
    private String firstName;
    private String lastName;
    private int age;
    private Gender gender;
    private String seatNumber;

    public enum Gender {
        MALE, FEMALE, OTHER
    }

    // Constructors
    public Passenger() {}

    public Passenger(int passengerId, int bookingId, String firstName, String lastName,
                     int age, Gender gender, String seatNumber) {
        this.passengerId = passengerId;
        this.bookingId = bookingId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.seatNumber = seatNumber;
    }

    // Getters and Setters
    public int getPassengerId() { return passengerId; }
    public void setPassengerId(int passengerId) { this.passengerId = passengerId; }

    public int getBookingId() { return bookingId; }
    public void setBookingId(int bookingId) { this.bookingId = bookingId; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public Gender getGender() { return gender; }
    public void setGender(Gender gender) { this.gender = gender; }

    public String getSeatNumber() { return seatNumber; }
    public void setSeatNumber(String seatNumber) { this.seatNumber = seatNumber; }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public String toString() {
        return getFullName() + " (" + age + "yrs, " + gender + ")";
    }
}
