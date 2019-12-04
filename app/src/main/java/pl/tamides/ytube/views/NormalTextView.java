package pl.tamides.ytube.views;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import pl.tamides.ytube.R;

public class NormalTextView extends BaseTextView {

    public NormalTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, R.layout.view_normal_text);
    }
}
