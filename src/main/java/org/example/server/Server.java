package org.example.server;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;
import org.example.atm.AtmController;
import org.example.game.GameController;
import org.example.transaction.TransactionController;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class Server {
    private static final String ATM_ENDPOINT = "/atms/calculateOrder";
    private static final String GAME_ENDPOINT = "/onlinegame/calculate";
    private static final String TRANSACTION_ENDPOINT = "/transactions/report";

    public void start() throws IOException {

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 300);

        HttpContext atmContext = server.createContext(ATM_ENDPOINT);
        atmContext.setHandler(new AtmController());

        HttpContext gameContext = server.createContext(GAME_ENDPOINT);
        gameContext.setHandler(new GameController());

        HttpContext transactionContext = server.createContext(TRANSACTION_ENDPOINT);
        transactionContext.setHandler(new TransactionController());

        server.setExecutor(Executors.newFixedThreadPool(4));

        server.start();
    }
}