package pl.tamides.ytube.views;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import pl.tamides.ytube.R;

public class BaseTextView extends FrameLayout {

    private TextView textView;

    public BaseTextView(@NonNull Context context, @Nullable AttributeSet attrs, int layoutRes) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BaseTextView, 0, 0);
        CharSequence text = typedArray.getText(R.styleable.BaseTextView_text);
        CharSequence toolsText = typedArray.getText(R.styleable.BaseTextView_android_text);
        boolean centerText = typedArray.getBoolean(R.styleable.BaseTextView_centerText, false);
        typedArray.recycle();

        FrameLayout rootView = (FrameLayout) LayoutInflater.from(context).inflate(layoutRes, this, false);
        textView = rootView.findViewById(R.id.text);

        if (text != null) {
            textView.setText(text);
        }

        if (centerText) {
            textView.setGravity(Gravity.CENTER);
        }

        if (isInEditMode()) {
            if (toolsText != null) {
                textView.setText(toolsText);
            }
        }

        addView(rootView);
    }

    public String getText() {
        return textView.getText().toString();
    }

    public BaseTextView setText(int stringRes) {
        textView.setText(stringRes);
        return this;
    }

    public BaseTextView setText(String text) {
        textView.setText(text);
        return this;
    }

    public BaseTextView setText(CharSequence text) {
        textView.setText(text);
        return this;
    }

    public BaseTextView setTextColor(ColorStateList color) {
        textView.setTextColor(color);
        return this;
    }

    public boolean hasText() {
        return textView.length() > 0;
    }
}
