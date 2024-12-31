package isw.dao;

import isw.releases.Album;
import org.json.JSONObject;
import org.json.JSONArray;
//import org.json.JSONObject;
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
import java.util.Locale;

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
                    String albumID = albumObj.has("mbid") && !albumObj.isNull("mbid") ? albumObj.getString("mbid") : null;
                    System.out.println("The ID for " + title + " by " + artist + " is: " + albumID);
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

    public Album getAlbumDetails(String artist, String albumTitle) {
        try {
            if (artist == null || artist.isEmpty() || albumTitle == null || albumTitle.isEmpty()) {
                System.err.println("Artist or album title is missing.");
                return null;
            }

            // Construct the API URL using artist and album name
            String url = "http://ws.audioscrobbler.com/2.0/?method=album.getinfo&api_key=" + LASTFM_API_KEY +
                    "&artist=" + URLEncoder.encode(artist, StandardCharsets.UTF_8.toString()) +
                    "&album=" + URLEncoder.encode(albumTitle, StandardCharsets.UTF_8.toString()) +
                    "&format=json";
            System.out.println("API URL: " + url);

            JSONObject response = makeApiRequest(url);
            if (response == null) {
                System.err.println("API response is null.");
                return null;
            }

            if (!response.has("album") || response.isNull("album")) {
                System.err.println("Album object is missing in the response.");
                return null;
            }

            JSONObject albumJson = response.getJSONObject("album");

            // Extract album details
            String title = albumJson.optString("name", "Unknown Title");

            // Handle "artist" as either a JSONObject or String
            String artistName;
            if (albumJson.has("artist")) {
                Object artistField = albumJson.get("artist");
                if (artistField instanceof JSONObject) {
                    artistName = ((JSONObject) artistField).optString("name", "Unknown Artist");
                } else if (artistField instanceof String) {
                    artistName = (String) artistField;
                } else {
                    artistName = "Unknown Artist";
                }
            } else {
                artistName = "Unknown Artist";
            }

            JSONArray imageArray = albumJson.optJSONArray("image");
            String coverUrl = (imageArray != null && imageArray.length() > 3) ?
                    imageArray.getJSONObject(3).optString("#text", DEFAULT_COVER_URL) : DEFAULT_COVER_URL;

            int trackCount = albumJson.has("tracks") ?
                    albumJson.getJSONObject("tracks").optJSONArray("track").length() : 0;

            String albumLength = "Unknown";
            if (albumJson.has("tracks") && albumJson.getJSONObject("tracks").has("track")) {
                JSONArray tracksArray = albumJson.getJSONObject("tracks").optJSONArray("track");
                if (tracksArray != null) {
                    albumLength = calculateAlbumLength(tracksArray);
                }
            }

            ArrayList<String> genres = new ArrayList<>();
            if (albumJson.has("tags") && albumJson.getJSONObject("tags").has("tag")) {
                JSONArray tagsArray = albumJson.getJSONObject("tags").getJSONArray("tag");
                for (int i = 0; i < tagsArray.length(); i++) {
                    JSONObject tag = tagsArray.getJSONObject(i);
                    String genre = tag.optString("name", null);
                    if (genre != null) {
                        genres.add(genre);
                    }
                }
            }

            // Handle the "wiki" field safely
            Date releaseDate = null;
            if (albumJson.has("wiki") && albumJson.optJSONObject("wiki") != null) {
                JSONObject wikiJson = albumJson.optJSONObject("wiki");
                releaseDate = parseDate(wikiJson.optString("published"));
            }

            return new Album(null, title, artistName, coverUrl, releaseDate, trackCount, albumLength, genres, null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Retrieves detailed information about an album using its MusicBrainz ID.
     *
     * @param albumId The Last.fm MusicBrainz ID of the album.
     * @return An Album object containing detailed information.
     */
    public Album getAlbumDetails(String albumId) {
        System.out.println("Fetching album details for ID: " + albumId);
        if (albumId == null || albumId.isEmpty()) {
            System.err.println("Invalid album ID: " + albumId);
            return null;
        }
        try {
            String url = "http://ws.audioscrobbler.com/2.0/?method=album.getinfo&api_key=" + LASTFM_API_KEY +
                    "&mbid=" + URLEncoder.encode(albumId, StandardCharsets.UTF_8.toString()) + "&format=json";

            JSONObject response = makeApiRequest(url);
            if (response != null) {
                JSONObject albumJson = response.getJSONObject("album");
                String title = albumJson.getString("name");
                //String artist = albumJson.getJSONObject("artist").getString("name");
                String artistName;
                if (albumJson.has("artist")) {
                    Object artistField = albumJson.get("artist");
                    if (artistField instanceof JSONObject) {
                        artistName = ((JSONObject) artistField).optString("name", "Unknown Artist");
                    } else if (artistField instanceof String) {
                        artistName = (String) artistField;
                    } else {
                        artistName = "Unknown Artist";
                    }
                } else {
                    artistName = "Unknown Artist";
                }

                String coverUrl = albumJson.getJSONArray("image").getJSONObject(3).getString("#text");
                if (coverUrl == null || coverUrl.isEmpty()) {
                    coverUrl = DEFAULT_COVER_URL;
                }
                int trackCount = albumJson.getJSONObject("tracks").getJSONArray("track").length();

                String albumLength = "Unknown";
                if (albumJson.has("tracks") && albumJson.getJSONObject("tracks").has("track")) {
                    JSONArray tracksArray = albumJson.getJSONObject("tracks").optJSONArray("track");
                    if (tracksArray != null) {
                        albumLength = calculateAlbumLength(tracksArray);
                    }
                }

                ArrayList<String> genres = new ArrayList<>();
                if (albumJson.has("tags") && albumJson.getJSONObject("tags").has("tag")) {
                    JSONArray tagsArray = albumJson.getJSONObject("tags").getJSONArray("tag");
                    for (int i = 0; i < tagsArray.length(); i++) {
                        JSONObject tag = tagsArray.getJSONObject(i);
                        String genre = tag.optString("name", null);
                        if (genre != null) {
                            genres.add(genre);
                        }
                    }
                }

                Date releaseDate = parseDate(albumJson.optString("wiki", "published"));

                return new Album(albumId, title, artistName, coverUrl, releaseDate, trackCount, albumLength, genres, null);
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
            if (dateString == null || dateString.isEmpty()) {
                System.err.println("Date string is null or empty.");
                return null;
            }

            // If the date string contains JSON-like content, extract only the "published" part
            if (dateString.contains("\"published\"")) {
                JSONObject wikiJson = new JSONObject(dateString);
                dateString = wikiJson.optString("published", "");
            }

            // Parse the extracted date
            if (!dateString.isEmpty()) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.ENGLISH);
                System.out.println(dateFormat.parse(dateString));
                return dateFormat.parse(dateString);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.err.println("Failed to parse date: " + dateString);
        return null;
    }

    private String calculateAlbumLength(JSONArray tracksArray) {
        int totalDuration = 0;

        for (int i = 0; i < tracksArray.length(); i++) {
            JSONObject track = tracksArray.getJSONObject(i);
            int trackDuration = track.optInt("duration", 0); // Get track duration, default to 0 if missing
            totalDuration += trackDuration;
        }

        // Convert totalDuration (seconds) to minutes:seconds format
        int minutes = totalDuration / 60;
        int seconds = totalDuration % 60;

        return String.format("%d:%02d", minutes, seconds); // Format as "mm:ss"
    }

}
