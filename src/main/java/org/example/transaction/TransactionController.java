package org.example.transaction;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public final class TransactionController implements HttpHandler {

    private final TransactionSerializer transactionSerializer = new TransactionSerializer();

    private final TransactionService transactionService = new TransactionService();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        final InputStream inputStream = exchange.getRequestBody();

        //Deserialize
        final List<Transaction> transactions = transactionSerializer.deserialize(inputStream);

        //Calculate
        final List<Account> accounts = transactionService.calculate(transactions);

        //Serialize
        final byte[] serialized = transactionSerializer.serialize(accounts);

        //Send response
        exchange.sendResponseHeaders(200, serialized.length);
        OutputStream outStream = exchange.getResponseBody();
        outStream.write(serialized);
        outStream.close();
    }
}