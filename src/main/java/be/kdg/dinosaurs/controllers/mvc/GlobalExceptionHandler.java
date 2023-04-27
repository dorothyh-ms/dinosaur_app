package be.kdg.dinosaurs.controllers.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;


@ControllerAdvice
public class GlobalExceptionHandler {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(DataAccessException.class)
    public String handleDatabaseException(Exception e){
        LOGGER.info("GlobalExceptionHandler is running handleDatabaseException");
        e.printStackTrace();
        LOGGER.error(e.getMessage());
        return "dberror";
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(Exception e) throws RuntimeException {
        LOGGER.info("GlobalExceptionHandler is running handleEntityNotFoundException");
        LOGGER.error(e.getMessage());
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        LOGGER.info("GlobalExceptionHandler is running defaultErrorHandler");
        if (AnnotationUtils.findAnnotation
                (e.getClass(), ResponseStatus.class) != null)
            throw e;
        ModelAndView mav = new ModelAndView();
        e.printStackTrace();
        mav.addObject("exception", e);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName("error");
        return mav;
    }
}
