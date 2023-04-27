package be.kdg.dinosaurs.controllers.mvc.converters;

import be.kdg.dinosaurs.domain.Diet;
import org.springframework.core.convert.converter.Converter;

public class StringToDietConverter implements Converter<String, Diet> {
    @Override
    public Diet convert(String source) {
        return switch (source.toLowerCase()) {
            case "herbivore" -> Diet.HERBIVORE;
            default -> Diet.CARNIVORE;
        };
    }
}
