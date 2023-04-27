package be.kdg.dinosaurs.domain;


import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="periods")
//Should the one-to-many be bidirectional??
public class Period {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="period_id")
    private int id;

    @Column(name="period_name", length=20, nullable=false)
    private String name;

    @Column(name="start_millions_years")
    private double startMillionsYears;

    @Column(name="end_millions_years")
    private double endMillionsYears;

    public Period(String name, double startMillionsYears, double endMillionsYears) {
        this.name = name;
        this.startMillionsYears = startMillionsYears;
        this.endMillionsYears = endMillionsYears;
    }


    @Override
    public String toString() {
        return name + " (" + startMillionsYears + " - "  +  endMillionsYears + " mya)";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Period period = (Period) o;
        return id == period.id && Double.compare(period.startMillionsYears, startMillionsYears) == 0 && Double.compare(period.endMillionsYears, endMillionsYears) == 0 && Objects.equals(name, period.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, startMillionsYears, endMillionsYears);
    }
}
