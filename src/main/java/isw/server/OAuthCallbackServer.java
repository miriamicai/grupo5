package isw.server;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import isw.dao.SpotifyAuth;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.HttpURLConnection;

public class OAuthCallbackServer {
    private static HttpServer server;

    public static void startServer() {
        if (server != null) {
            System.out.println("Server is currently running.");
            return; // Avoid starting multiple instances
        }
        try {
            server = HttpServer.create(new InetSocketAddress(8080), 0);
            server.createContext("/callback", new HttpHandler() {
                @Override
                public void handle(HttpExchange exchange) throws IOException {
                    String query = exchange.getRequestURI().getQuery();
                    if (query != null && query.contains("code=")) {
                        String code = query.split("code=")[1].split("&")[0];
                        System.out.println("Authorization code received: " + code);

                        // Trigger the token exchange
                        SpotifyAuth.exchangeCodeForToken(code);

                        // Respond to the browser
                        String response = "Authorization complete. You may close this window.";
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, response.length());
                        OutputStream os = exchange.getResponseBody();
                        os.write(response.getBytes());
                        os.close();

                        // Stop the server after processing
                        stopServer();
                    }
                }
            });
            server.start();
            System.out.println("Server listening on http://localhost:8080/callback");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to start the server.");
        }
    }

    public static boolean isServerRunning() {
        return server != null;
    }

    public static void stopServer() {
        if (server != null) {
            server.stop(0);
            server = null; // Set to null so it can be restarted if needed
            System.out.println("Server stopped.");
        }
    }
}