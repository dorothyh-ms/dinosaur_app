package be.kdg.dinosaurs.domain;

import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class DiscoveryId implements Serializable {

    @ManyToOne
    @JoinColumn(name="species_id", nullable=false)
    private Species species;

    @ManyToOne
    @JoinColumn(name="digsite_id", nullable=false)
    private DigSite digSite;


}
