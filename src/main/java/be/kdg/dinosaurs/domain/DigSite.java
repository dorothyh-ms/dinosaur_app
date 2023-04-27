package be.kdg.dinosaurs.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="digsites")
public class DigSite {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="digsite_id")
    private int id;

    @Column(name="digsite_name")
    private String name;

    private String country;
    private double latitude;
    private double longitude;


    @Column(name="first_excavation_date")
    private LocalDate firstExcavation;

    @OneToMany(mappedBy="id.digSite", orphanRemoval = true)
    private List<Discovery> discoveries;


    public DigSite(String name, String country, double latitude, double longitude, LocalDate firstExcavation) {
        this.name = name;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
        this.firstExcavation = firstExcavation;
    }


    @Override
    public String toString() {
        return name;
    }

}
