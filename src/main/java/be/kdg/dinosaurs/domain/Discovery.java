package be.kdg.dinosaurs.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;



@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="discoveries")
public class Discovery {

    @EmbeddedId
    private DiscoveryId id;

    @Column(name="date_discovered")
    private LocalDate dateDiscovered;
}
