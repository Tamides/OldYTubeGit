package pl.tamides.ytube.pages.subscriptions;

import java.util.List;

import pl.tamides.ytube.R;
import pl.tamides.ytube.api.api_models.Subscription;
import pl.tamides.ytube.helpers.ApiHelper;
import pl.tamides.ytube.pages.BasePage;
import pl.tamides.ytube.views.ListView;

public class Subscriptions extends BasePage {

    private ListView subscriptions;

    public Subscriptions() {
        setContentView(R.layout.page_subscriptions);

        ApiHelper.getInstance().getSubscriptions(new ApiHelper.ResultReceiver<List<Subscription>>() {
            @Override
            public void onReceive(List<Subscription> results) {
                if (results == null) {
                    results = results;
                }
            }
        });

        subscriptions = findViewById(R.id.subscriptions);
    }
}
