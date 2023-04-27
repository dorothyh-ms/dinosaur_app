package be.kdg.dinosaurs.controllers.mvc.viewmodel;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SpeciesUserFavoriteViewModel {
    private int speciesId;

    private String speciesName;

    private String dateFavorited;

    private String imageURL;
}
