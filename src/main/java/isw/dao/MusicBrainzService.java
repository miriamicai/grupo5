
package isw.dao;

import isw.releases.Album;
import org.json.JSONObject;
import org.json.JSONArray;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

public class MusicBrainzService {

    private static final String USER_AGENT = "Soulmate/1.0 ( your-email@example.com )";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final String LASTFM_API_KEY = "c02461157b7f2bb67aa1771a5eb40f33";

    /**
     * Searches for albums based on the album name.
     *
     * @param albumName The name of the album to search for.
     * @return A list of Album objects matching the search criteria.
     */
    public List<Album> searchAlbum(String albumName) throws UnsupportedEncodingException {
        String encodedAlbumName = URLEncoder.encode(albumName, StandardCharsets.UTF_8.toString());
        String url = "https://musicbrainz.org/ws/2/release-group/?query=release-group:" + encodedAlbumName + "&fmt=json";

        JSONObject response = makeApiRequest(url);
        return parseMainAlbum(response);
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

            // Check for a successful response code (200 OK)
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                //System.out.println("Resource not found: " + urlString);
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
     * Parses a JSON response containing album search results.
     *
     * @param response The JSON response from MusicBrainz's album search.
     * @return A list of Album objects.
     */
    private List<Album> parseMainAlbum(JSONObject response) {
        List<Album> albums = new ArrayList<>();
        if (response != null) {
            JSONArray items = response.getJSONArray("release-groups");
            for (int i = 0; i < items.length(); i++) {
                JSONObject item = items.getJSONObject(i);

                // Filter by primary-type "Album" to ensure it's a studio album
                String primaryType = item.optString("primary-type", "");
                if (!primaryType.equals("Album")) continue;

                String releaseGroupId = item.getString("id");
                String title = item.getString("title");

                // Check if artist-credit is present
                String artistName = "Unknown Artist"; // Default if artist-credit is missing
                if (item.has("artist-credit")) {
                    JSONArray artistCreditArray = item.getJSONArray("artist-credit");
                    if (artistCreditArray.length() > 0) {
                        artistName = artistCreditArray.getJSONObject(0).getJSONObject("artist").getString("name");
                    }
                }

                // Find an official release within the group
                JSONObject release = findOfficialRelease(releaseGroupId);
                if (release != null) {
                    albums.add(new Album(
                            release.getString("id"),
                            title,
                            artistName,
                            fetchCoverURL(release.getString("id")),
                            parseDate(release.optString("date")),
                            release.optInt("track-count", 0),
                            releaseGroupId // Set the release-group ID
                    ));
                }
            }
        }
        return albums;
    }


    /**
     * Helper method to find the official release within a release group.
     *
     * @param releaseGroupId The ID of the release group.
     * @return A JSONObject for the official release, or null if not found.
     */
    private JSONObject findOfficialRelease(String releaseGroupId) {
        String url = "https://musicbrainz.org/ws/2/release-group/" + releaseGroupId + "?inc=releases&fmt=json";
        JSONObject response = makeApiRequest(url);
        if (response != null) {
            JSONArray releases = response.getJSONArray("releases");
            for (int i = 0; i < releases.length(); i++) {
                JSONObject release = releases.getJSONObject(i);
                String status = release.optString("status", "");
                if (status.equals("Official")) {
                    return release;
                }
            }
            // Fallback: if no "Official" release, return the first release in the group
            if (releases.length() > 0) {
                return releases.getJSONObject(0);
            }
        }
        return null;
    }


    /**
     * Parses a JSON response for detailed album information.
     *
     * @param response The JSON response from MusicBrainz's album details endpoint.
     * @return An Album object with detailed information.
     */
    private Album parseAlbumDetails(JSONObject response) {
        if (response != null) {
            String releaseGroupId = response.optString("release-group", null); // Adjust the key if necessary
            return new Album(
                    response.getString("id"),
                    response.getString("title"),
                    response.getJSONArray("artist-credit").getJSONObject(0).getJSONObject("artist").getString("name"),
                    null,  // Placeholder for cover URL
                    parseDate(response.optString("date")),
                    response.optInt("track-count", 0),
                    releaseGroupId
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

    /**
     * Fetches the cover art URL for an album using Last.fm's API.
     *
     * @param albumId The MusicBrainz ID of the album.
     * @return The URL of the album cover, or a default placeholder URL if not found.
     */
    private String fetchCoverURL(String albumId) {
        try {
            // First, attempt to use Last.fm's album.getInfo endpoint with the MusicBrainz ID
            String urlStr = "http://ws.audioscrobbler.com/2.0/?method=album.getinfo&api_key=" + LASTFM_API_KEY +
                    "&mbid=" + URLEncoder.encode(albumId, StandardCharsets.UTF_8.toString()) + "&format=json";

            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", USER_AGENT);

            // Check if the response code is 200 (OK)
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder content = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();
                connection.disconnect();

                // Parse JSON response to find the cover URL
                JSONObject jsonResponse = new JSONObject(content.toString());
                JSONArray images = jsonResponse.getJSONObject("album").getJSONArray("image");

                // Retrieve the large cover image (or adjust to preferred size)
                for (int i = 0; i < images.length(); i++) {
                    JSONObject image = images.getJSONObject(i);
                    if ("large".equals(image.getString("size"))) {
                        String coverUrl = image.getString("#text");
                        if (!coverUrl.isEmpty()) {
                            //System.out.println("Cover found for album ID " + albumId + ": " + coverUrl);
                            return coverUrl; // Return the large cover URL
                        }
                    }
                }
            } else {
                //System.out.println("Cover not found for album ID " + albumId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Return the placeholder image if no cover is found
        return "src/main/resources/default_cover_174x174.png";
    }
}
