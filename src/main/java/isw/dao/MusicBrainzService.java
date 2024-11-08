package isw.dao;

import isw.releases.Album;
import org.json.JSONObject;
import org.json.JSONArray;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

public class MusicBrainzService {

    private static final String USER_AGENT = "Soulmate/1.0 ( your-email@example.com )";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Searches for albums based on the album name.
     *
     * @param albumName The name of the album to search for.
     * @return A list of Album objects matching the search criteria.
     */
    public List<Album> searchAlbum(String albumName) {
        String url = "https://musicbrainz.org/ws/2/release/?query=" + albumName + "&fmt=json";

        JSONObject response = makeApiRequest(url);
        return parseAlbums(response);
    }

    /**
     * Retrieves detailed information about an album using its MusicBrainz ID.
     *
     * @param albumId The MusicBrainz ID of the album.
     * @return An Album object containing detailed information.
     */
    public Album getAlbumDetails(String albumId) {
        String url = "https://musicbrainz.org/ws/2/release/" + albumId + "?fmt=json";

        JSONObject response = makeApiRequest(url);
        return parseAlbumDetails(response);
    }

    /**
     * Retrieves information about an artist by their MusicBrainz ID.
     *
     * @param artistId The MusicBrainz ID of the artist.
     * @return An Artist object containing information about the artist.
     */
    /*public Artist getArtistDetails(String artistId) {
        String url = "https://musicbrainz.org/ws/2/artist/" + artistId + "?fmt=json";

        JSONObject response = makeApiRequest(url);
        return parseArtistDetails(response);
    }*/

    /**
     * Helper method to make an API request to MusicBrainz and return the JSON response.
     *
     * @param urlString The URL to make the request to.
     * @return A JSONObject containing the response from the MusicBrainz API.
     */
    private JSONObject makeApiRequest(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", USER_AGENT);
            connection.setRequestProperty("Content-Type", "application/json");

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
     * Parses a JSON response containing album search results.
     *
     * @param response The JSON response from MusicBrainz's album search.
     * @return A list of Album objects.
     */
    private List<Album> parseAlbums(JSONObject response) {
        List<Album> albums = new ArrayList<>();
        if (response != null) {
            JSONArray items = response.getJSONArray("releases");
            for (int i = 0; i < items.length(); i++) {
                JSONObject item = items.getJSONObject(i);
                albums.add(new Album(
                        item.getString("id"),
                        item.getString("title"),
                        item.getJSONArray("artist-credit").getJSONObject(0).getJSONObject("artist").getString("name"),
                        null,  // Placeholder for cover URL, as MusicBrainz does not provide cover images directly
                        parseDate(item.optString("date")),
                        item.optInt("track-count", 0)
                ));
            }
        }
        return albums;
    }

    /**
     * Parses a JSON response for detailed album information.
     *
     * @param response The JSON response from MusicBrainz's album details endpoint.
     * @return An Album object with detailed information.
     */
    private Album parseAlbumDetails(JSONObject response) {
        if (response != null) {
            return new Album(
                    response.getString("id"),
                    response.getString("title"),
                    response.getJSONArray("artist-credit").getJSONObject(0).getJSONObject("artist").getString("name"),
                    null,  // Placeholder for cover URL
                    parseDate(response.optString("date")),
                    response.optInt("track-count", 0)
            );
        }
        return null;
    }

    /**
     * Parses a JSON response for artist information.
     *
     * @param response The JSON response from MusicBrainz's artist endpoint.
     * @return An Artist object containing information about the artist.
     */
    /*private Artist parseArtistDetails(JSONObject response) {
        if (response != null) {
            return new Artist(
                    response.getString("id"),
                    response.getString("name"),
                    response.optString("disambiguation", "No genre info"),
                    null  // MusicBrainz API doesn't provide images directly
            );
        }
        return null;
    }*/

    /**
     * Parses a date string in "yyyy-MM-dd" format to a Date object.
     *
     * @param dateString The date string to parse.
     * @return A Date object, or null if parsing fails or date is empty.
     */
    private Date parseDate(String dateString) {
        try {
            if (dateString.matches("\\d{4}")) { // Year only
                return new SimpleDateFormat("yyyy").parse(dateString);
            } else if (dateString.matches("\\d{4}-\\d{2}")) { // Year and month
                return new SimpleDateFormat("yyyy-MM").parse(dateString);
            } else if (dateString.matches("\\d{4}-\\d{2}-\\d{2}")) { // Full date
                return DATE_FORMAT.parse(dateString);
            } else {
                return null; // Return null if the date format is unrecognized
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
