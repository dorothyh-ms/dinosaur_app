package be.kdg.dinosaurs.controllers.mvc.viewmodel;

import be.kdg.dinosaurs.domain.Diet;
import be.kdg.dinosaurs.domain.Period;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SpeciesViewModel {

    private int id;

    private String name;

    private String scientificName;

    private int numberSpecimensFound;

    private Diet diet;

    private String periodName;

    private double periodStartMillionsYears;
    private double periodEndMillionsYears;

    private int numberOfDigSites;

    private int numberOfFavorites;

    private String image;

}
