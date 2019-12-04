package pl.tamides.ytube.views;

import android.content.Context;
import android.transition.Fade;
import android.transition.TransitionManager;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.ContentFrameLayout;

import pl.tamides.ytube.App;

public class BaseDialogView<T> extends FrameLayout {

    protected ContentFrameLayout activityContainer;
    private Fade fadeAnimation;

    public BaseDialogView(@NonNull Context context) {
        super(context);

        fadeAnimation = new Fade();
        activityContainer = App.getCurrentActivity().findViewById(android.R.id.content);
    }

    public T show() {
        TransitionManager.beginDelayedTransition(activityContainer, fadeAnimation);
        activityContainer.addView(this);
        return (T) this;
    }

    public void dismiss() {
        TransitionManager.beginDelayedTransition(activityContainer, fadeAnimation);
        activityContainer.removeView(this);
    }
}
