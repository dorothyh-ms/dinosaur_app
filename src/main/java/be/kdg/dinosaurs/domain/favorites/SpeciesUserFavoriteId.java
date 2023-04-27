package be.kdg.dinosaurs.domain.favorites;

import be.kdg.dinosaurs.domain.Species;
import be.kdg.dinosaurs.domain.User;
import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class SpeciesUserFavoriteId implements Serializable {

    @ManyToOne
    @JoinColumn(name="species_id", nullable=false)
    private Species species;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;
}
