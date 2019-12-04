package pl.tamides.ytube.api.youtube_api_models;

import java.util.List;

public class YSubscription {

    private String nextPageToken;
    private PageInfo pageInfo;
    private List<Item> items;

    public String getNextPageToken() {
        return nextPageToken;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public List<Item> getItems() {
        return items;
    }

    public class PageInfo {

        private int totalResults;
        private int resultsPerPage;

        public int getTotalResults() {
            return totalResults;
        }

        public int getResultsPerPage() {
            return resultsPerPage;
        }
    }

    public class Item {

        private Snippet snippet;

        public Snippet getSnippet() {
            return snippet;
        }
    }

    public class Snippet {

        private String title;
        private ResourceId resourceId;
        private Thumbnails thumbnails;

        public String getTitle() {
            return title;
        }

        public ResourceId getResourceId() {
            return resourceId;
        }

        public Thumbnails getThumbnails() {
            return thumbnails;
        }
    }

    public class ResourceId {

        private String channelId;

        public String getChannelId() {
            return channelId;
        }
    }

    public class Thumbnails {

        private High high;

        public High getHigh() {
            return high;
        }
    }

    public class High {

        private String url;

        public String getUrl() {
            return url;
        }
    }
}
