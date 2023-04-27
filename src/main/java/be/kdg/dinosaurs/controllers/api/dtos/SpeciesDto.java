package be.kdg.dinosaurs.controllers.api.dtos;

import be.kdg.dinosaurs.domain.Diet;
import be.kdg.dinosaurs.domain.Period;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SpeciesDto {

    private int id;
    private String name;

    private String scientificName;
    private int numberSpecimensFound;

    private Diet diet;

    private PeriodDto period;


}
