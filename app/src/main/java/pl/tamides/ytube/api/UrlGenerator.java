package pl.tamides.ytube.api;

public class UrlGenerator {

    private static volatile UrlGenerator instance = null;

    public static UrlGenerator getInstance() {
        if (instance == null) {
            synchronized (UrlGenerator.class) {
                if (instance == null) {
                    instance = new UrlGenerator();
                }
            }
        }

        return instance;
    }

    public String getSubscriptionsUrl(String nextPageToken) {
        String url = "https://www.googleapis.com/youtube/v3/subscriptions?part=snippet&maxResults=50&mine=true";

        if (nextPageToken != null) {
            url += "&pageToken=" + nextPageToken;
        }

        return url;
    }
}
