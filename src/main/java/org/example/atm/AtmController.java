package org.example.atm;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public final class AtmController implements HttpHandler {

    private final AtmSerializer atmSerializer = new AtmSerializer();

    private final AtmService atmService = new AtmService();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        InputStream inputStream = exchange.getRequestBody();

        //Deserialize
        List<Task> tasks = atmSerializer.deserialize(inputStream);

        //Calculate
        List<Task> atms = atmService.calculateOrder(tasks);

        //Serialize
        byte[] serialized = atmSerializer.serialize(atms);

        //Send response
        exchange.sendResponseHeaders(200, serialized.length);
        OutputStream outStream = exchange.getResponseBody();
        outStream.write(serialized);
        outStream.close();
    }
}