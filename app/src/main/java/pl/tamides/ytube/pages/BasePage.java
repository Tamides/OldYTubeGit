package pl.tamides.ytube.pages;

import android.view.LayoutInflater;
import android.widget.FrameLayout;

import pl.tamides.ytube.App;

public class BasePage extends FrameLayout {

    public BasePage() {
        super(App.getCurrentActivity());
    }

    public void onBg(Runnable runnable) {
        App.getCurrentActivity().onBg(runnable);
    }

    public void onUi(Runnable runnable) {
        App.getCurrentActivity().onUi(runnable);
    }

    public void setContentView(int layoutRes) {
        addView(LayoutInflater.from(App.getCurrentActivity()).inflate(layoutRes, null, false));
    }
}
