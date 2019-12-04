package pl.tamides.ytube.views;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import pl.tamides.ytube.App;
import pl.tamides.ytube.R;

public class ButtonView extends FrameLayout {

    private ConstraintLayout rootView;
    private NormalTextView normalTextView;

    public ButtonView() {
        super(App.getCurrentActivity());
        init(App.getCurrentActivity(), null);
    }

    public ButtonView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        rootView = (ConstraintLayout) LayoutInflater.from(context).inflate(R.layout.view_button, this, false);
        normalTextView = rootView.findViewById(R.id.button);

        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ButtonView, 0, 0);
            ColorStateList textColor = typedArray.getColorStateList(R.styleable.ButtonView_textColor);
            CharSequence text = typedArray.getText(R.styleable.ButtonView_text);
            typedArray.recycle();

            if (textColor != null) {
                normalTextView.setTextColor(textColor);
            }

            if (text != null) {
                normalTextView.setText(text);
            }
        }

        addView(rootView);
    }

    public String getText() {
        return normalTextView.getText();
    }

    public ButtonView setText(int stringRes) {
        normalTextView.setText(stringRes);
        return this;
    }

    public ButtonView setText(String text) {
        normalTextView.setText(text);
        return this;
    }

    public ButtonView setOnClick(View.OnClickListener onClickListener) {
        normalTextView.setOnClickListener(onClickListener);
        return this;
    }

    public boolean hasText() {
        return normalTextView.hasText();
    }

    public ButtonView removeFrame() {
        rootView.setVisibility(INVISIBLE);
        return this;
    }

    public ButtonView removeClickAnimation() {
        normalTextView.setBackground(null);
        return this;
    }
}
