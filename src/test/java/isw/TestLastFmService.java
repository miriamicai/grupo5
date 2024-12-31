package isw;

import isw.dao.LastFmServiceUI;
import isw.releases.Album;
import isw.releases.Artist;
import isw.releases.Song;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class TestLastFmService {

    private LastFmServiceUI lastFmService;

    @Before
    public void setUp() {
        lastFmService = Mockito.spy(new LastFmServiceUI());
    }

    @Test
    public void testSearchAlbum() {
        // Simular respuesta de la API
        Mockito.doReturn(Arrays.asList(
                new Album("1", "Album1", "Artist1", "http://image1.com", null, 10, "1"),
                new Album("2", "Album2", "Artist2", "http://image2.com", null, 8, "2")
        )).when(lastFmService).searchAlbum("test");

        // Ejecutar el método
        List<Album> albums = lastFmService.searchAlbum("test");

        // Validar resultados
        assertNotNull(albums);
        assertEquals(2, albums.size());
        assertEquals("Album1", albums.get(0).getTitle());
        assertEquals("Artist1", albums.get(0).getArtist());
        assertEquals("http://image1.com", albums.get(0).getCoverUrl());
    }

    @Test
    public void testSearchArtists() {
        // Simular respuesta de la API
        Mockito.doReturn(Arrays.asList(
                new Artist("Artist1", "http://image1.com"),
                new Artist("Artist2", "http://image2.com")
        )).when(lastFmService).searchArtists();

        // Ejecutar el método
        List<Artist> artists = lastFmService.searchArtists();

        // Validar resultados
        assertNotNull(artists);
        assertEquals(2, artists.size());
        assertEquals("Artist1", artists.get(0).getName());
        assertEquals("http://image1.com", artists.get(0).getImageUrl());
    }

    @Test
    public void testGetNewSongs() {
        // Simular respuesta de la API
        Mockito.doReturn(Arrays.asList(
                new Song("Song1", "Artist1", "http://image1.com"),
                new Song("Song2", "Artist2", "http://image2.com")
        )).when(lastFmService).getNewSongs();

        // Ejecutar el método
        List<Song> songs = lastFmService.getNewSongs();

        // Validar resultados
        assertNotNull(songs);
        assertEquals(2, songs.size());
        assertEquals("Song1", songs.get(0).getTitle());
        assertEquals("Artist1", songs.get(0).getArtist());
    }

    @Test
    public void testGetTopTracks() {
        // Simular respuesta de la API
        Mockito.doReturn(Map.of(
                "Song1 - Artist1", 1000,
                "Song2 - Artist2", 2000
        )).when(lastFmService).getTopTracks();

        // Ejecutar el método
        Map<String, Integer> topTracks = lastFmService.getTopTracks();

        // Validar resultados
        assertNotNull(topTracks);
        assertEquals(2, topTracks.size());
        assertTrue(topTracks.containsKey("Song1 - Artist1"));
        assertEquals(1000, (int) topTracks.get("Song1 - Artist1"));
    }
}