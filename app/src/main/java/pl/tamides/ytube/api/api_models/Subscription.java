package pl.tamides.ytube.api.api_models;

import pl.tamides.ytube.api.youtube_api_models.YSubscription;

public class Subscription {

    private String title;
    private String channelId;
    private String thumbnailUrl;

    public static Subscription from(YSubscription.Snippet snippet) {
        return new Subscription()
                .setTitle(snippet.getTitle())
                .setChannelId(snippet.getResourceId().getChannelId())
                .setThumbnailUrl(snippet.getThumbnails().getHigh().getUrl());
    }

    public String getTitle() {
        return title;
    }

    public Subscription setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getChannelId() {
        return channelId;
    }

    public Subscription setChannelId(String channelId) {
        this.channelId = channelId;
        return this;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public Subscription setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
        return this;
    }
}
