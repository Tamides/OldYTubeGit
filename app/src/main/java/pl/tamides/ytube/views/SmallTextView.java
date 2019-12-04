package pl.tamides.ytube.views;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import pl.tamides.ytube.R;

public class SmallTextView extends BaseTextView {

    public SmallTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, R.layout.view_small_text);
    }
}
