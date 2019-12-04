package pl.tamides.ytube.helpers;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import pl.tamides.ytube.App;
import pl.tamides.ytube.R;
import pl.tamides.ytube.api.UrlGenerator;
import pl.tamides.ytube.api.api_models.Subscription;
import pl.tamides.ytube.api.youtube_api_models.YSubscription;
import pl.tamides.ytube.views.ProgressDialogView;

public class ApiHelper {

    private static volatile ApiHelper instance = null;

    private OkHttpClient okHttpClient;
    private Request.Builder requestBuilder;
    private Gson gson;

    public ApiHelper() {
        okHttpClient = new OkHttpClient();
        requestBuilder = new Request.Builder();
        gson = new Gson();
    }

    public static ApiHelper getInstance() {
        if (instance == null) {
            synchronized (ApiHelper.class) {
                if (instance == null) {
                    instance = new ApiHelper();
                }
            }
        }

        return instance;
    }

    private String getJson(String url, String accessToken) {
        try {
            Response response = okHttpClient
                    .newCall(
                            requestBuilder
                                    .url(url)
                                    .addHeader("Authorization", String.format("Bearer %s", accessToken))
                                    .build()
                    ).execute();

            if (!response.isSuccessful()) {
                App.showMessage(response.body().string());
                return null;
            }

            return response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
            App.showMessage(e.getMessage());
            return null;
        }
    }

    public void getSubscriptions(ResultReceiver<List<Subscription>> resultReceiver) {
        GoogleAutorizationHelper.getInstance().executeWithToken(token -> {
            ProgressDialogView progressDialogView = new ProgressDialogView()
                    .setMessage(ResourcesHelper.getInstance().getString(R.string.downloading) + "...")
                    .setProgress(0)
                    .show();

            onBg(() -> {
                int totalResults;
                UrlGenerator urlGenerator = UrlGenerator.getInstance();
                String json = getJson(urlGenerator.getSubscriptionsUrl(null), token);

                if (json == null) {
                    onUi(progressDialogView::dismiss);
                    return;
                }

                List<Subscription> results = new ArrayList<>();
                YSubscription ySubscription = gson.fromJson(json, YSubscription.class);

                totalResults = ySubscription.getPageInfo().getTotalResults();

                for (YSubscription.Item item : ySubscription.getItems()) {
                    results.add(Subscription.from(item.getSnippet()));
                    updateProgress(progressDialogView, ResourcesHelper.getInstance().getString(R.string.downloading) + " " + results.size() + "/" + totalResults, getProgress(results.size(), totalResults));
                }

                String nextPageToken = ySubscription.getNextPageToken();

                while (nextPageToken != null) {
                    json = getJson(urlGenerator.getSubscriptionsUrl(nextPageToken), token);

                    if (json == null) {
                        onUi(progressDialogView::dismiss);
                        return;
                    }

                    ySubscription = gson.fromJson(json, YSubscription.class);

                    for (YSubscription.Item item : ySubscription.getItems()) {
                        results.add(Subscription.from(item.getSnippet()));
                        updateProgress(progressDialogView, ResourcesHelper.getInstance().getString(R.string.downloading) + " " + results.size() + "/" + totalResults, getProgress(results.size(), totalResults));
                    }

                    nextPageToken = ySubscription.getNextPageToken();
                }

                onUi(() -> {
                    progressDialogView.dismiss();
                    resultReceiver.onReceive(results);
                });
            });
        });
    }

    private int getProgress(int processedItemsCount, int allItemsCount) {
        return (processedItemsCount * 100) / allItemsCount;
    }

    private void updateProgress(ProgressDialogView progressDialogView, String progressDialogMessage, int progress) {
        onUi(new Runnable() {
            @Override
            public void run() {
                progressDialogView
                        .setMessage(progressDialogMessage)
                        .setProgress(progress);
            }
        });
    }

    private void onBg(Runnable runnable) {
        App.getCurrentActivity().onBg(runnable);
    }

    private void onUi(Runnable runnable) {
        App.getCurrentActivity().onUi(runnable);
    }

    public interface ResultReceiver<T> {
        void onReceive(T results);
    }
}
