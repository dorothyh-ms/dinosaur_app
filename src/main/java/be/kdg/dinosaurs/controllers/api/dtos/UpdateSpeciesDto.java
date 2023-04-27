package be.kdg.dinosaurs.controllers.api.dtos;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class UpdateSpeciesDto {


    @NotBlank(message="{dinosaurs.errors.scientificNameNotBlankError}")
    @Pattern(regexp="^\\w{2,}+\\s\\w{2,}$", message = "{dinosaurs.errors.scientificNamePatternError}")
    private String scientificName;
    @Min(value=1, message="{dinosaurs.errors.numberSpecimensFoundError}")
    private int numberOfSpecimensFound;

    private String periodName;

    private String dietName;

    public UpdateSpeciesDto() {
    }

    public UpdateSpeciesDto(String scientificName, int numberOfSpecimensFound, String periodName, String dietName) {
        this.scientificName = scientificName;
        this.numberOfSpecimensFound = numberOfSpecimensFound;
        this.dietName = dietName;
        this.periodName = periodName;
    }

    public int getNumberOfSpecimensFound() {
        return numberOfSpecimensFound;
    }

    public void setNumberOfSpecimensFound(int numberOfSpecimensFound) {
        this.numberOfSpecimensFound = numberOfSpecimensFound;
    }

    public String getScientificName() {
        return scientificName;
    }

    public void setScientificName(String scientificName) {
        this.scientificName = scientificName;
    }

    public String getDietName() {
        return dietName;
    }

    public void setDietName(String dietName) {
        this.dietName = dietName;
    }

    public String getPeriodName() {
        return periodName;
    }

    public void setPeriodName(String periodName) {
        this.periodName = periodName;
    }
}

