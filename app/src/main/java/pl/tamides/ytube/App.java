package pl.tamides.ytube;

import android.app.Application;
import android.content.Context;

import pl.tamides.ytube.views.MessageDialogView;

public class App extends Application {

    private static Context appContext = null;
    private static BaseActivity currentActivity = null;

    public static Context getAppContext() {
        return appContext;
    }

    public static BaseActivity getCurrentActivity() {
        return currentActivity;
    }

    public static void setCurrentActivity(BaseActivity currentActivity) {
        App.currentActivity = currentActivity;
    }

    public static void showMessage(String message) {
        currentActivity.onUi(() -> new MessageDialogView()
                .setMessage(message)
                .setPositiveButton(R.string.ok, null)
                .setCancelable(false)
                .show());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
    }
}
