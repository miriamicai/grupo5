package isw.dao;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import isw.ui.JVentana;
import org.json.JSONObject;

import isw.server.OAuthCallbackServer;
import okhttp3.*;

import javax.swing.*;

public class SpotifyAuth {
    private static final String CLIENT_ID = "7c7d1d3242384a038f34d77b1402a7d6";
    private static final String CLIENT_SECRET = "ca0c3553a7a34641954a2c796bb11288";
    private static final String REDIRECT_URI = "http://localhost:8080/callback";
    private static final String SCOPE = "user-library-read"; // Adjust as needed
    private static final String API_URL = "https://api.spotify.com/v1/me";
    private static final String API_URL_ALBUMS = "https://api.spotify.com/v1/me/albums";

    public static String accessToken;


    public static void startAuthorizationFlow() {
        // Start the HTTP server to handle the callback
        OAuthCallbackServer.startServer();

        // Open the Spotify authorization page in the browser
        try {
            String authUrl = "https://accounts.spotify.com/authorize"
                    + "?response_type=code"
                    + "&client_id=" + CLIENT_ID
                    + "&redirect_uri=" + URLEncoder.encode(REDIRECT_URI, StandardCharsets.UTF_8.name())
                    + "&scope=" + URLEncoder.encode(SCOPE, StandardCharsets.UTF_8.name());

            System.out.println("Opening the authorization page...");
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI(authUrl));
            } else {
                System.out.println("Desktop is not supported. Please copy and paste the URL into your browser: " + authUrl);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error generating or opening the authorization URL: " + e.getMessage());
        }
    }

    public static void requestNewAuthorizationCode() {
        if (OAuthCallbackServer.isServerRunning()) {
            System.out.println("Server is already running.");
        } else {
            OAuthCallbackServer.startServer();  // Start server if not running
        }
        startAuthorizationFlow();  // Start the authorization flow
    }

    public static void exchangeCodeForToken(String code) {
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("grant_type", "authorization_code")
                .add("code", code)
                .add("redirect_uri", REDIRECT_URI)
                .add("client_id", CLIENT_ID)
                .add("client_secret", CLIENT_SECRET)
                .build();

        Request request = new Request.Builder()
                .url("https://accounts.spotify.com/api/token")
                .post(formBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                System.out.println("Response body: " + responseBody);
                JSONObject jsonObject = new JSONObject(responseBody);
                accessToken = jsonObject.getString("access_token");
                System.out.println("Access token obtained: " + accessToken);

                // Schedule the GUI update on the Event Dispatch Thread (EDT)
                SwingUtilities.invokeLater(() -> {
                    //JVentana mainWindow = (JVentana) JFrame.getFrames()[0]; // Get the main frame
                    //mainWindow.showAccountInfoButton(); // Replace the button
                    getUserAccountInfo(accessToken);
                });
            } else {
                System.out.println("Failed to get access token: " + response.message());
                System.out.println("Response code: " + response.code());
                String errorBody = response.body().string();
                System.out.println("Error response body: " + errorBody);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void getUserAccountInfo(String accessToken) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(API_URL)
                .header("Authorization", "Bearer " + accessToken)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                JSONObject obj = new JSONObject(responseBody);
                String username = obj.optString("display_name", "Unknown User");
                String email = obj.optString("email", "No email available");
                String country = obj.optString("country", "No country available");
                int followers = obj.optJSONObject("followers") != null
                        ? obj.optJSONObject("followers").optInt("total", 0)
                        : 0;

                System.out.println("User account info:");
                System.out.println("Username: " + username);
                System.out.println("Email: " + email);
                System.out.println("Country: " + country);
                System.out.println("Followers: " + followers);
            } else {
                System.out.println("Failed to get user account info: " + response.message());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}