package isw.releases;

public class Artist {
    private String name;
    private String imageUrl;

    public Artist(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl != null && !imageUrl.isEmpty() ? imageUrl : "src/main/resources/default_artist_image.png";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "Artist{" +
                "name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
