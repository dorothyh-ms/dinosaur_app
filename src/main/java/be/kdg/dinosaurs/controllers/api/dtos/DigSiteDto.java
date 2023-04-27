package be.kdg.dinosaurs.controllers.api.dtos;

import javax.persistence.Column;
import java.time.LocalDate;

public class DigSiteDto {

    private int id;
    private String name;

    private String country;
    private double latitude;
    private double longitude;

    private LocalDate firstExcavation;

    public DigSiteDto() {
    }


    public DigSiteDto(int id, String name, String country, double latitude, double longitude, LocalDate firstExcavation) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
        this.firstExcavation = firstExcavation;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public LocalDate getFirstExcavation() {
        return firstExcavation;
    }

    public void setFirstExcavation(LocalDate firstExcavation) {
        this.firstExcavation = firstExcavation;
    }
}
