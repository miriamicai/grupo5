package isw.dao;

import isw.releases.Album;
import isw.releases.Artist;
import isw.releases.Song;
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
import java.util.HashMap;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Map;

public class LastFmService {

    private static final String USER_AGENT = "Soulmate/1.0 ( your-email@example.com )";
    private static final String LASTFM_API_KEY = "c02461157b7f2bb67aa1771a5eb40f33";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private final String DEFAULT_COVER_URL = "src/main/resources/default_cover_174x174.png";
    private final String DEFAULT_IMAGE_URL = "src/main/resources/default_artist.png";

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
                    String coverUrl = albumObj.getJSONArray("image").getJSONObject(2).optString("#text", DEFAULT_COVER_URL);
                    String albumID = albumObj.optString("mbid", null);

                    albums.add(new Album(albumID, title, artist, coverUrl, null, 0, null, null, null));
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
                return null;
            }

            String url = "http://ws.audioscrobbler.com/2.0/?method=album.getinfo&api_key=" + LASTFM_API_KEY +
                    "&artist=" + URLEncoder.encode(artist, StandardCharsets.UTF_8.toString()) +
                    "&album=" + URLEncoder.encode(albumTitle, StandardCharsets.UTF_8.toString()) +
                    "&format=json";

            JSONObject response = makeApiRequest(url);
            if (response == null || !response.has("album")) {
                return null;
            }

            JSONObject albumJson = response.getJSONObject("album");
            String title = albumJson.optString("name", "Unknown Title");
            String artistName = albumJson.optString("artist", "Unknown Artist");

            JSONArray imageArray = albumJson.optJSONArray("image");
            String coverUrl = (imageArray != null && imageArray.length() > 3) ?
                    imageArray.getJSONObject(3).optString("#text", DEFAULT_COVER_URL) : DEFAULT_COVER_URL;

            int trackCount = albumJson.has("tracks") ?
                    albumJson.getJSONObject("tracks").optJSONArray("track").length() : 0;

            String albumLength = calculateAlbumLength(albumJson.optJSONObject("tracks"));

            ArrayList<String> genres = extractGenres(albumJson.optJSONObject("tags"));

            Date releaseDate = parseDate(albumJson.optString("wiki", "published"));

            return new Album(null, title, artistName, coverUrl, releaseDate, trackCount, albumLength, genres, null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

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
                    String imageUrl = artistObj.getJSONArray("image").getJSONObject(2).optString("#text", DEFAULT_IMAGE_URL);

                    artists.add(new Artist(name, imageUrl));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return artists;
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
                    String coverUrl = trackObj.getJSONArray("image").getJSONObject(2).optString("#text", DEFAULT_COVER_URL);

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

            JSONObject response = makeApiRequest(url);
            if (response != null) {
                JSONArray tracks = response.getJSONObject("tracks").getJSONArray("track");

                for (int i = 0; i < tracks.length(); i++) {
                    JSONObject track = tracks.getJSONObject(i);
                    String title = track.getString("name") + " - " + track.getJSONObject("artist").getString("name");
                    int playCount = track.getInt("playcount");
                    topTracks.put(title, playCount);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return topTracks;
    }

    private JSONObject makeApiRequest(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", USER_AGENT);

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return null;
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder content = new StringBuilder();
            String inputLine;
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

    private Date parseDate(String dateString) {
        try {
            if (dateString == null || dateString.isEmpty()) {
                return null;
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.ENGLISH);
            return dateFormat.parse(dateString);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String calculateAlbumLength(JSONObject tracksObj) {
        if (tracksObj == null || !tracksObj.has("track")) {
            return "Unknown";
        }

        int totalDuration = 0;
        JSONArray tracksArray = tracksObj.optJSONArray("track");
        for (int i = 0; i < tracksArray.length(); i++) {
            totalDuration += tracksArray.getJSONObject(i).optInt("duration", 0);
        }

        int minutes = totalDuration / 60;
        int seconds = totalDuration % 60;
        return String.format("%d:%02d", minutes, seconds);
    }

    private ArrayList<String> extractGenres(JSONObject tagsObj) {
        ArrayList<String> genres = new ArrayList<>();
        if (tagsObj == null || !tagsObj.has("tag")) {
            return genres;
        }

        JSONArray tagsArray = tagsObj.optJSONArray("tag");
        for (int i = 0; i < tagsArray.length(); i++) {
            String genre = tagsArray.getJSONObject(i).optString("name", null);
            if (genre != null) {
                genres.add(genre);
            }
        }
        return genres;
    }
}
