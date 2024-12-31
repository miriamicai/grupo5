package isw.releases;

import java.util.*;

public class Album {
    private String id, length;
    private String title;
    private String artist;
    private String coverUrl;
    private String releaseGroupID;
    private Date release;
    private int numTracks, avgRating, personalRating;
    private ArrayList<String> genres;

    public String getReleaseGroupID() {
        return releaseGroupID;
    }

    public void setReleaseGroupID(String releaseGroupID) {
        this.releaseGroupID = releaseGroupID;
    }

    public int getNumTracks() {
        return numTracks;
    }

    public void setNumTracks(int numTracks) {
        this.numTracks = numTracks;
    }

    public int getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(int avgRating) {
        this.avgRating = avgRating;
    }

    public int getPersonalRating() {
        return personalRating;
    }

    public void setPersonalRating(int personalRating) {
        this.personalRating = personalRating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public Date getRelease() {
        return release;
    }

    public void setRelease(Date release) {
        this.release = release;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    @Override
    public String toString() {
        return "Album{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                ", releaseDate=" + release +
                ", trackCount=" + numTracks +
                '}';
    }

    public Album(String id, String title, String artist, String coverUrl, Date release, int numTracks, String length, ArrayList<String> genres, String releaseGroupID) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.coverUrl = coverUrl;
        this.release = release;
        this.numTracks = numTracks;
        this.length = length;
        this.genres = genres;
        this.releaseGroupID = releaseGroupID;
    }
}
