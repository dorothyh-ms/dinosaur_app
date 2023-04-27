package be.kdg.dinosaurs.controllers.api.dtos;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NewSpeciesDto {

    @NotBlank(message="{dinosaurs.errors.nameNotBlankError}")
    @Size(min=2, max=50, message="{dinosaurs.errors.nameSizeError}")
    private String name;

    @NotBlank(message="{dinosaurs.errors.scientificNameNotBlankError}")
    @Pattern(regexp="^\\w{2,}+\\s\\w{2,}$", message = "{dinosaurs.errors.scientificNamePatternError}")
    private String scientificName;

    @Min(value=1, message="{dinosaurs.errors.numberSpecimensFoundError}")
    private int numberOfSpecimensFound;

    @NotBlank(message = "{dinosaurs.errors.imageError}")
    private String image;

    private String dietName;

    private String periodName;

    private Map<Integer, LocalDate> digSiteIdsDatesFound;

}
