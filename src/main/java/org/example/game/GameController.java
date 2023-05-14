package org.example.game;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public final class GameController implements HttpHandler {

    private final GameSerializer gameSerializer = new GameSerializer();

    private final GameService gameService = new GameService();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        InputStream inputStream = exchange.getRequestBody();

        //Deserialize
        Players players = gameSerializer.deserialize(inputStream);

        //Calculate
        List<List<Clan>> atms = gameService.calculateOrder(players);

        //Serialize
        byte[] serialized = gameSerializer.serialize(atms);

        //Send response
        exchange.sendResponseHeaders(200, serialized.length);
        OutputStream outStream = exchange.getResponseBody();
        outStream.write(serialized);
        outStream.close();
    }
}