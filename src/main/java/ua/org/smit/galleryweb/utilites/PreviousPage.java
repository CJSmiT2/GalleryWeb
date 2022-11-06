package ua.org.smit.galleryweb.utilites;

import java.util.Hashtable;
import java.util.Map;
import java.util.Optional;

public class PreviousPage {

    private final Hashtable<String, String> lastPage = new Hashtable<String, String>();
    private final Hashtable<String, String> lastAlbum = new Hashtable<String, String>();

    public void addLasPage(String url, String sessionId) {
        lastPage.put(sessionId, url);
    }

    public void addLastAlbum(String url, String sessionId) {
        lastPage.put(sessionId, url);
    }

    public Optional<String> getLastPage(String sessionId) {
        for (Map.Entry<String, String> entry : lastPage.entrySet()) {
            if (entry.getKey().equals(sessionId)) {
                return Optional.of(entry.getValue());
            }
        }
        return Optional.empty();
    }

    public Optional<String> getLastAlbum(String sessionId) {
        for (Map.Entry<String, String> entry : lastAlbum.entrySet()) {
            if (entry.getKey().equals(sessionId)) {
                return Optional.of(entry.getValue());
            }
        }
        return Optional.empty();
    }
}
