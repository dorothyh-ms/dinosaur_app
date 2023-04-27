package be.kdg.dinosaurs.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such digsite")
public class DigSiteNotAvailableException extends RuntimeException {
    public DigSiteNotAvailableException(String message){
        super(message);
    }
}
