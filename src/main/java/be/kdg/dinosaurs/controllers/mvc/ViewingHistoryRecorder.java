package be.kdg.dinosaurs.controllers.mvc;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


public final class ViewingHistoryRecorder {
    public static void addHistory(HttpSession httpSession, String url){
        if (httpSession.getAttribute("session_history") == null){
            httpSession.setAttribute("session_history", new ArrayList<String[]>());
        };
        List<String[]> historyList = (ArrayList<String[]>) httpSession.getAttribute("session_history");
        String[] historyTuple = {new Timestamp(httpSession.getLastAccessedTime()).toString(), url};
        historyList.add(historyTuple);
        httpSession.setAttribute("session_history", historyList);
    }
}
