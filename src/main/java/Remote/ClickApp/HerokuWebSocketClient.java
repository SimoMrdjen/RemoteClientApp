package Remote.ClickApp;


import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.function.Consumer;

public class HerokuWebSocketClient extends WebSocketClient {

    private final Consumer<String> messageHandler;

    public HerokuWebSocketClient(URI serverUri, Consumer<String> messageHandler) {
        super(serverUri);
        this.messageHandler = messageHandler;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("Connected to Heroku WebSocket server.");
    }

    @Override
    public void onMessage(String message) {
        System.out.println("Received message: " + message);
        // Trigger the message handler (this will update the UI)
        messageHandler.accept(message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Disconnected from Heroku WebSocket server.");
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
    }

    public static HerokuWebSocketClient connectToHeroku(Consumer<String> messageHandler) throws URISyntaxException {
        URI uri = new URI("wss://micic-fc43cdd8f709.herokuapp.com/click"); // Adjust to your Heroku WebSocket URL
        HerokuWebSocketClient client = new HerokuWebSocketClient(uri, messageHandler);
        client.connect();
        return client;
    }
}

