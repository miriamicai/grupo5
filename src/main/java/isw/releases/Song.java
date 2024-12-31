package isw.releases;

public class Song {
    private String title;
    private String artist;
    private String coverUrl;

    public Song(String title, String artist, String coverUrl) {
        this.title = title;
        this.artist = artist;
        this.coverUrl = coverUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getCoverUrl() {
        return coverUrl;
    }
}
