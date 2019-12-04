package pl.tamides.ytube.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ContainerView extends FrameLayout {

    public ContainerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void replaceView(View newView) {
        removeViewAt(0);
        addView(newView);
    }
}
