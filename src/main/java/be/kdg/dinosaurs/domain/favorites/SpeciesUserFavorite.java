package be.kdg.dinosaurs.domain.favorites;

import lombok.*;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.time.LocalDateTime;


@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SpeciesUserFavorite {

    @EmbeddedId
    private SpeciesUserFavoriteId id;

    private LocalDateTime dateFavorited;
}
