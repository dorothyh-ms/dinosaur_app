package be.kdg.dinosaurs.controllers.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class LoginController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/login")
    public String showLogin(){
        LOGGER.info("LoginController is running showLogin");
        return "login";
    }

}
