package be.kdg.dinosaurs.domain;

import be.kdg.dinosaurs.domain.favorites.SpeciesUserFavorite;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="species")
public class Species {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="species_id")
    private int id;

    @Column(name="species_name", nullable=false, length=30)
    private String name;

    @Column(name="scientific_name", nullable=false, length=50)
    private String scientificName;
    @Column(name="number_specimens_found")
    private int numberSpecimensFound;

    @Enumerated(EnumType.STRING)
    private Diet diet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="period_id")
    private Period period;

    @Column(name="image_url", length=300)
    private String image;

    @ManyToOne
    private User author;


    @OneToMany(mappedBy="id.species", orphanRemoval = true)
    private List<Discovery> discoveries;

    @OneToMany(mappedBy="id.species", orphanRemoval = true)
    private List<SpeciesUserFavorite> speciesUserFavorites;


    public Species(String name, String scientificName, int numberSpecimensFound, Diet diet, String image) {
        this.name = name;
        this.scientificName = scientificName;
        this.numberSpecimensFound = numberSpecimensFound;
        this.diet = diet;
        this.image = image;

    }

    public Species(String name, String scientificName, int numberSpecimensFound, Diet diet) {
        this.name = name;
        this.scientificName = scientificName;
        this.numberSpecimensFound = numberSpecimensFound;
        this.diet = diet;
    }

    @Override
    public String toString() {
        return name + " (" + scientificName + ")" ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Species species = (Species) o;
        return id == species.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
