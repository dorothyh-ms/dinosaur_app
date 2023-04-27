package be.kdg.dinosaurs.configuration;


import be.kdg.dinosaurs.controllers.mvc.viewmodel.SpeciesUserFavoriteViewModel;
import be.kdg.dinosaurs.controllers.mvc.viewmodel.SpeciesViewModel;
import be.kdg.dinosaurs.domain.Species;
import be.kdg.dinosaurs.domain.favorites.SpeciesUserFavorite;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;

import java.time.format.DateTimeFormatter;


@Configuration
public class SpeciesConfiguration {
    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();

        Converter<Species, SpeciesViewModel> speciesConverter = new AbstractConverter<>() {
            @Override
            protected SpeciesViewModel convert(Species source) {
                if (source == null)
                    return null;
                SpeciesViewModel destination = new SpeciesViewModel();

                destination.setId(source.getId());
                destination.setName(source.getName());
                destination.setScientificName(source.getScientificName());
                destination.setDiet(source.getDiet());
                destination.setPeriodName(source.getPeriod().getName());
                destination.setNumberSpecimensFound(source.getNumberSpecimensFound());
                destination.setNumberOfDigSites(source.getDiscoveries().size());
                destination.setNumberOfFavorites(source.getSpeciesUserFavorites().size());
                destination.setImage(source.getImage());
                destination.setPeriodStartMillionsYears(source.getPeriod().getStartMillionsYears());
                destination.setPeriodEndMillionsYears(source.getPeriod().getEndMillionsYears());
                return destination;
            }
        };

        Converter<SpeciesUserFavorite, SpeciesUserFavoriteViewModel> speciesUserFavoriteConverter = new AbstractConverter<>() {
            @Override
            protected SpeciesUserFavoriteViewModel convert(SpeciesUserFavorite source) {
                if (source == null)
                    return null;
                SpeciesUserFavoriteViewModel destination = new SpeciesUserFavoriteViewModel();
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm yyyy-MM-dd ");
                String formattedDateTime = source.getDateFavorited().format(dateTimeFormatter);
                destination.setSpeciesId(source.getId().getSpecies().getId());
                destination.setSpeciesName(source.getId().getSpecies().getName());
                destination.setDateFavorited(formattedDateTime);
                destination.setImageURL(source.getId().getSpecies().getImage());
                return destination;
            }
        };
        modelMapper.addConverter(speciesConverter);
        modelMapper.addConverter(speciesUserFavoriteConverter);

        return modelMapper;
    }
}
