package org.example.game;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class GameController implements HttpHandler {
    GameSerializer gameSerializer = new GameSerializer();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        InputStream inputStream = exchange.getRequestBody();

        Players players = gameSerializer.deserialize(inputStream);

        List<List<Clan>> atms = new GameService().calculateOrder(players);

        byte[] serialized = gameSerializer.serialize(atms);

        exchange.sendResponseHeaders(200, serialized.length);
        OutputStream outStream = exchange.getResponseBody();
        outStream.write(serialized);
        outStream.close();
    }

}
