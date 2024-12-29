package isw.dao;

import isw.releases.Album;
import org.json.JSONObject;
import org.json.JSONArray;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

public class LastFmService {

    private static final String USER_AGENT = "Soulmate/1.0 ( your-email@example.com )";
    private static final String LASTFM_API_KEY = "c02461157b7f2bb67aa1771a5eb40f33";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private final String DEFAULT_COVER_URL = "src/main/resources/default_cover_174x174.png";

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
                        coverUrl = DEFAULT_COVER_URL;
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
     * Retrieves detailed information about an album using its MusicBrainz ID.
     *
     * @param albumId The Last.fm MusicBrainz ID of the album.
     * @return An Album object containing detailed information.
     */
    public Album getAlbumDetails(String albumId) {
        try {
            String url = "http://ws.audioscrobbler.com/2.0/?method=album.getinfo&api_key=" + LASTFM_API_KEY +
                    "&mbid=" + URLEncoder.encode(albumId, StandardCharsets.UTF_8.toString()) + "&format=json";

            JSONObject response = makeApiRequest(url);
            if (response != null) {
                JSONObject albumJson = response.getJSONObject("album");
                String title = albumJson.getString("name");
                String artist = albumJson.getJSONObject("artist").getString("name");
                String coverUrl = albumJson.getJSONArray("image").getJSONObject(3).getString("#text");
                if (coverUrl == null || coverUrl.isEmpty()) {
                    coverUrl = DEFAULT_COVER_URL;
                }
                int trackCount = albumJson.getJSONObject("tracks").getJSONArray("track").length();
                Date releaseDate = parseDate(albumJson.optString("wiki", "published"));

                return new Album(albumId, title, artist, coverUrl, releaseDate, trackCount, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
                return null; // Return null if the response is not 200 OK
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            connection.disconnect();

            return new JSONObject(content.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Parses a date string in "yyyy-MM-dd" format to a Date object.
     *
     * @param dateString The date string to parse.
     * @return A Date object, or null if parsing fails or date is empty.
     */
    private Date parseDate(String dateString) {
        try {
            if (dateString != null && !dateString.isEmpty()) {
                return DATE_FORMAT.parse(dateString);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
