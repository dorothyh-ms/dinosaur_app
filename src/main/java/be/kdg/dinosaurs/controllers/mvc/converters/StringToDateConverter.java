package be.kdg.dinosaurs.controllers.mvc.converters;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class StringToDateConverter implements Converter<String, LocalDate> {
    @Override
    public LocalDate convert(String source) {
        if (!source.isEmpty()) {
            System.out.println(source);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(source, formatter);
            return date;
        } return null;
    }
}


