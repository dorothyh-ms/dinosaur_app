package be.kdg.dinosaurs.controllers.mvc.viewmodel;

import be.kdg.dinosaurs.domain.Diet;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashMap;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EditSpeciesViewModel {

    @NotBlank(message="{dinosaurs.errors.nameNotBlankError}")
    @Size(min=2, max=50, message="{dinosaurs.errors.nameSizeError}")
    private String name;

    @NotBlank(message="{dinosaurs.errors.scientificNameNotBlankError}")
    @Pattern(regexp="^\\w{2,}+\\s\\w{2,}$", message = "{dinosaurs.errors.scientificNamePatternError}")
    private String scientificName;

    @Min(value=1, message="{dinosaurs.errors.numberSpecimensFoundError}")
    private int numberSpecimensFound;

    private Diet diet;

    private String periodName;
    @NotBlank(message = "{dinosaurs.errors.imageError}")
    private String image;

    private HashMap<Integer, LocalDate> digSiteIdsDatesFound;

}
