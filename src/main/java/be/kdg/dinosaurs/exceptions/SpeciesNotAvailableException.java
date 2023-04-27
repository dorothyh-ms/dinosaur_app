package be.kdg.dinosaurs.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


 @ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such species")
public class SpeciesNotAvailableException extends RuntimeException {

    public SpeciesNotAvailableException(String message){
        super(message);
    }
}
