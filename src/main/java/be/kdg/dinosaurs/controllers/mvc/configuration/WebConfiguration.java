package be.kdg.dinosaurs.controllers.mvc.configuration;

//import be.kdg.dinosaurs.dinosaurs.controllers.converters.StringToDateConverter;
import be.kdg.dinosaurs.controllers.mvc.converters.StringToDateConverter;
import be.kdg.dinosaurs.controllers.mvc.converters.StringToDietConverter;
import be.kdg.dinosaurs.controllers.mvc.converters.StringToPeriodConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToDateConverter());
        registry.addConverter(new StringToDietConverter());
        registry.addConverter(new StringToPeriodConverter());
    }
}