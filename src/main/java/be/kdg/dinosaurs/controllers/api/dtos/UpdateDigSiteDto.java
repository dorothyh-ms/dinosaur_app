package be.kdg.dinosaurs.controllers.api.dtos;

import javax.validation.constraints.*;
import java.time.LocalDate;

public class UpdateDigSiteDto {
    @NotBlank(message = "{digsites.errors.countryError}")
    private String country;
    @DecimalMin(value="-90.0", message="{digsites.errors.latitudeError}")
    @DecimalMax(value="90.0", message="{digsites.errors.latitudeError}")
    private double latitude;


    @DecimalMin(value="-180.0", message="{digsites.errors.longitudeError}")
    @DecimalMax(value="180.0", message="{digsites.errors.longitudeError}")
    private double longitude;

    @NotNull(message="{digsites.errors.dateFirstExcavatedNull}")
    @PastOrPresent(message = "{digsites.errors.dateFirstExcavatedError}")
    private LocalDate firstExcavation;

    public UpdateDigSiteDto(String country, double latitude, double longitude, LocalDate firstExcavation) {
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
        this.firstExcavation = firstExcavation;
    }

    public UpdateDigSiteDto() {
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
