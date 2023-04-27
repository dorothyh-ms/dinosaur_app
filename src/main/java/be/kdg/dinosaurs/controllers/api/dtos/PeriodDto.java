package be.kdg.dinosaurs.controllers.api.dtos;

import javax.persistence.Column;

public class PeriodDto {

    private String name;

    private double startMillionsYears;

    private double endMillionsYears;

    public PeriodDto(String name, double startMillionsYears, double endMillionsYears) {
        this.name = name;
        this.startMillionsYears = startMillionsYears;
        this.endMillionsYears = endMillionsYears;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getStartMillionsYears() {
        return startMillionsYears;
    }

    public void setStartMillionsYears(double startMillionsYears) {
        this.startMillionsYears = startMillionsYears;
    }

    public double getEndMillionsYears() {
        return endMillionsYears;
    }

    public void setEndMillionsYears(double endMillionsYears) {
        this.endMillionsYears = endMillionsYears;
    }
}
