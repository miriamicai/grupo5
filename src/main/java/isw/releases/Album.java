package isw.releases;

import java.util.*;

public class Album {
    private String id, title, artist, coverUrl;
    private Date release;
    private int numTracks, avgRating, personalRating;

    public String getTitle(){return title;}
    public String getArtist(){return artist;}
    public String getId(){return id;}

    public String getCoverUrl() {
        return coverUrl;
    }

    public Date getRelease() {
        return release;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public void setRelease(Date release) {
        this.release = release;
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

    public Album(String id, String title, String artist, String coverUrl, Date release, int numTracks) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.coverUrl = coverUrl;
        this.release = release;
        this.numTracks = numTracks;
    }
}
