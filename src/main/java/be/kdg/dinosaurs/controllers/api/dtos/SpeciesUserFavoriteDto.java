package be.kdg.dinosaurs.controllers.api.dtos;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SpeciesUserFavoriteDto {

    private UserDto favoritedBy;

    private SpeciesDto species;

    private LocalDateTime dateFavorited;
}
