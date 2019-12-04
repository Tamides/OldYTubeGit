package pl.tamides.ytube;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;

import pl.tamides.ytube.helpers.GoogleAutorizationHelper;
import pl.tamides.ytube.helpers.ResourcesHelper;
import pl.tamides.ytube.pages.main.Main;
import pl.tamides.ytube.pages.subscriptions.Subscriptions;
import pl.tamides.ytube.views.ButtonView;
import pl.tamides.ytube.views.ContainerView;
import pl.tamides.ytube.views.ListView;
import pl.tamides.ytube.views.MessageDialogView;

public class MainActivity extends BaseActivity {

    private static final String ACTION_FOR_RESULT = "pl.tamides.ytube.GOOGLE_AUTHORIZATION_RESPONSE";
    private static final String ACTION_DEFAULT = "android.intent.action.MAIN";
    private static final Tab[] tabs = {
            Tab.MAIN,
            Tab.SUBSCRIPTIONS,
            Tab.PLAYLISTS
    };

    private ContainerView container;
    private ListView tabList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        container = findViewById(R.id.container);
        tabList = ((ListView<Tab, ButtonView>) findViewById(R.id.tabs))
                .setLayoutMng(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false))
                .setItemViewClass(ButtonView.class)
                .setItems(Arrays.asList(tabs))
                .enableHorizontalSpaceDivider()
                .setOnBindViewHolder((item, view) -> view.setText(item.getValue())
                        .setOnClick(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                switch (item) {
                                    case MAIN:
                                        container.replaceView(new Main());
                                        break;
                                    case SUBSCRIPTIONS:
                                        container.replaceView(new Subscriptions());
                                        break;
                                    case PLAYLISTS:

                                        break;
                                    default:
                                        new MessageDialogView()
                                                .setMessage(view.getText())
                                                .setPositiveButton(R.string.ok, null)
                                                .setCancelable(false)
                                                .show();
                                }
                            }
                        }))
                .confirmChanges();

        container.addView(new Main());
    }

    @Override
    protected void onResume() {
        super.onResume();

        String intentAction = getIntent().getAction();

        onBg(() -> {
            GoogleAutorizationHelper googleAutorizationHelper = GoogleAutorizationHelper.getInstance();

            if (TextUtils.equals(intentAction, ACTION_FOR_RESULT) && !googleAutorizationHelper.isAuthorized()) {
                onUi(() -> {
                    getIntent().setAction(ACTION_DEFAULT);

                    onBg(() -> GoogleAutorizationHelper.getInstance().handleResponse(getIntent(), () -> onUi(this::onResume)));
                });

                return;
            }

            if (!googleAutorizationHelper.isAuthorized()) {
                onUi(() -> new MessageDialogView()
                        .setMessage(R.string.you_are_not_logged_in)
                        .setPositiveButton(R.string.log_in, v -> {
                            GoogleAutorizationHelper.getInstance().launchAutorization(ACTION_FOR_RESULT);
                            finish();
                        })
                        .setCancelable(false)
                        .show());
            }
        });
    }

    public enum Tab {
        MAIN(ResourcesHelper.getInstance().getString(R.string.main)),
        SUBSCRIPTIONS(ResourcesHelper.getInstance().getString(R.string.subscriptions)),
        PLAYLISTS(ResourcesHelper.getInstance().getString(R.string.playlists));

        private String value;

        Tab(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
