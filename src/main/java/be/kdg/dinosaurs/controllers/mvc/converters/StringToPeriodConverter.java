package be.kdg.dinosaurs.controllers.mvc.converters;

import be.kdg.dinosaurs.domain.Period;
import org.springframework.core.convert.converter.Converter;

public class StringToPeriodConverter implements Converter<String, Period> {


    public Period convert(String source) {
        return switch (source.toLowerCase()) {
            case "cretaceous" -> new Period("Cretaceous", 145.0, 100.5);
            case "jurassic" -> new Period("Jurassic", 201.3, 145.0);
            default -> new Period("Triassic", 251.902, 201.36);
        };
    }
}
