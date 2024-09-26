package Remote.ClickApp;



import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ClickEventController {

    private List<String> eventLogs = new ArrayList<>();
    private HerokuWebSocketClient webSocketClient;

    @PostConstruct
    public void init() {
        // Connect to the Heroku WebSocket server and listen for click events
        try {
            webSocketClient = HerokuWebSocketClient.connectToHeroku(this::handleClickEvent);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    // Handle incoming click events from Heroku
    private void handleClickEvent(String message) {
        if ("performClick".equals(message)) {
            eventLogs.add("Click event received at " + System.currentTimeMillis());
        }
    }

    // Serve the UI and display the logs
    @GetMapping("/")
    public String showEventLogs(Model model) {
        model.addAttribute("logs", eventLogs);
        return "index";
    }
}
