package isw.dao;

import isw.releases.Album;
import isw.releases.Artist;
import isw.releases.Song;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LastFmServiceUI {

    private static final String USER_AGENT = "Soulmate/1.0 (your-email@example.com)";
    private static final String LASTFM_API_KEY = "c02461157b7f2bb67aa1771a5eb40f33";
    private final String DEFAULT_IMAGE_URL = "src/main/resources/default_artist.png";

    /**
     * Searches for albums based on the album name using Last.fm API.
     *
     * @param albumName The name of the album to search for.
     * @return A list of Album objects matching the search criteria.
     */
    public List<Album> searchAlbum(String albumName) {
        List<Album> albums = new ArrayList<>();
        try {
            String encodedAlbumName = URLEncoder.encode(albumName, StandardCharsets.UTF_8.toString());
            String url = "http://ws.audioscrobbler.com/2.0/?method=album.search&album=" + encodedAlbumName +
                    "&api_key=" + LASTFM_API_KEY + "&format=json";

            JSONObject response = makeApiRequest(url);
            if (response != null) {
                JSONArray albumArray = response.getJSONObject("results")
                        .getJSONObject("albummatches")
                        .getJSONArray("album");

                for (int i = 0; i < albumArray.length(); i++) {
                    JSONObject albumObj = albumArray.getJSONObject(i);
                    String title = albumObj.getString("name");
                    String artist = albumObj.getString("artist");
                    String coverUrl = albumObj.getJSONArray("image").getJSONObject(2).getString("#text");
                    if (coverUrl == null || coverUrl.isEmpty()) {
                        coverUrl = DEFAULT_IMAGE_URL;
                    }

                    albums.add(new Album(null, title, artist, coverUrl, null, 0, null));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return albums;
    }

    /**
     * Searches for top artists using Last.fm API.
     *
     * @return A list of Artist objects containing top artists.
     */
    public List<Artist> searchArtists() {
        List<Artist> artists = new ArrayList<>();
        try {
            String url = "http://ws.audioscrobbler.com/2.0/?method=chart.gettopartists&api_key=" + LASTFM_API_KEY + "&format=json";

            JSONObject response = makeApiRequest(url);
            if (response != null) {
                JSONArray artistArray = response.getJSONObject("artists").getJSONArray("artist");

                for (int i = 0; i < artistArray.length(); i++) {
                    JSONObject artistObj = artistArray.getJSONObject(i);
                    String name = artistObj.getString("name");
                    String imageUrl = artistObj.getJSONArray("image").getJSONObject(2).getString("#text");
                    if (imageUrl == null || imageUrl.isEmpty()) {
                        imageUrl = DEFAULT_IMAGE_URL;
                    }

                    artists.add(new Artist(name, imageUrl));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return artists;
    }

    /**
     * Fetches popular or trending songs from Last.fm API.
     *
     * @return A list of Song objects representing new or trending songs.
     */
    public List<Song> getNewSongs() {
        List<Song> songs = new ArrayList<>();
        try {
            String url = "http://ws.audioscrobbler.com/2.0/?method=chart.gettoptracks&api_key=" + LASTFM_API_KEY + "&format=json";

            JSONObject response = makeApiRequest(url);
            if (response != null) {
                JSONArray tracksArray = response.getJSONObject("tracks").getJSONArray("track");

                for (int i = 0; i < tracksArray.length(); i++) {
                    JSONObject trackObj = tracksArray.getJSONObject(i);
                    String title = trackObj.getString("name");
                    String artist = trackObj.getJSONObject("artist").getString("name");
                    String coverUrl = trackObj.getJSONArray("image").getJSONObject(2).getString("#text");

                    songs.add(new Song(title, artist, coverUrl));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return songs;
    }

    public Map<String, Integer> getTopTracks() {
        Map<String, Integer> topTracks = new HashMap<>();
        try {
            String url = "http://ws.audioscrobbler.com/2.0/?method=chart.gettoptracks&api_key=" + LASTFM_API_KEY + "&format=json&limit=10";

            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", USER_AGENT);

            if (conn.getResponseCode() == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) {
                    response.append(line);
                }
                in.close();

                JSONObject jsonResponse = new JSONObject(response.toString());
                JSONArray tracks = jsonResponse.getJSONObject("tracks").getJSONArray("track");

                for (int i = 0; i < tracks.length(); i++) {
                    JSONObject track = tracks.getJSONObject(i);
                    String title = track.getString("name") + " - " + track.getJSONObject("artist").getString("name");
                    int playCount = track.getInt("playcount");
                    topTracks.put(title, playCount);
                }
            } else {
                System.err.println("Error al conectar con la API de Last.fm. Código: " + conn.getResponseCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return topTracks;
    }

    /**
     * Helper method to make an API request to Last.fm and return the JSON response.
     *
     * @param urlString The URL to make the request to.
     * @return A JSONObject containing the response from the Last.fm API.
     */
    private JSONObject makeApiRequest(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", USER_AGENT);

            // Check for a successful response code (200 OK)
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                System.err.println("HTTP Error: " + connection.getResponseCode());
                return null;
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            connection.disconnect();

            return new JSONObject(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
