package be.kdg.dinosaurs.controllers.mvc.viewmodel;

import javax.validation.constraints.*;
import java.time.LocalDate;

public class DigSiteViewModel {

    @NotBlank(message="{digsites.errors.nameNotBlankError}")
    @Size(min=2, max=50, message="{digsites.errors.nameSizeError}")
    private String name;
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

    @Override
    public String toString() {
        return "DigSiteViewModel{" +
                "name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", firstExcavation='" + firstExcavation + '\'' +
                '}';
    }
}
