package org.example.atm;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public final class AtmController implements HttpHandler {
    private final AtmSerializer atmSerializer = new AtmSerializer();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        InputStream inputStream = exchange.getRequestBody();

        List<Task> tasks = atmSerializer.deserialize(inputStream);

        List<Task> atms = new AtmService().calculateOrder(tasks);

        byte[] serialized = atmSerializer.serialize(atms);

        exchange.sendResponseHeaders(200, serialized.length);
        OutputStream outStream = exchange.getResponseBody();
        outStream.write(serialized);
        outStream.close();
    }
}