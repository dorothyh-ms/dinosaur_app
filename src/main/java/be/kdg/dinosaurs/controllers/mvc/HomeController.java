package be.kdg.dinosaurs.controllers.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static be.kdg.dinosaurs.controllers.mvc.ViewingHistoryRecorder.addHistory;

@Controller
@RequestMapping("/")
public class HomeController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @GetMapping()
    public String showHome(HttpSession httpSession){
        LOGGER.info("HomeController is running showHome");
        addHistory(httpSession, "/");
        return "index";
    }
    @GetMapping("sessionhistory")
    public String showSessionHistory(HttpSession httpSession, Model model){
        LOGGER.info("HomeController is running showSessionHistory");
        addHistory(httpSession, "/sessionhistory");
        List<String[]> records = (ArrayList<String[]>) httpSession.getAttribute("session_history");
        model.addAttribute("records", records);
        return "sessionhistory";
    }


}
