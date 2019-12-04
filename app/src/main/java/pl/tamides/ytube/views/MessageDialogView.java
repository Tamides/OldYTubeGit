package pl.tamides.ytube.views;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Space;

import androidx.constraintlayout.widget.ConstraintLayout;

import pl.tamides.ytube.App;
import pl.tamides.ytube.R;

public class MessageDialogView extends BaseDialogView<MessageDialogView> {

    private NormalTextView message;
    private ButtonView positive;
    private ButtonView negative;
    private Space messageStartMargin;
    private Space messageTopMargin;
    private Space messageEndMargin;
    private Space messageBottomMargin;

    private boolean cancelable = true;

    public MessageDialogView() {
        super(App.getCurrentActivity());

        ConstraintLayout rootView = (ConstraintLayout) LayoutInflater.from(App.getCurrentActivity()).inflate(R.layout.view_message_dialog, activityContainer, false);
        message = rootView.findViewById(R.id.message);
        positive = rootView.findViewById(R.id.positive);
        negative = rootView.findViewById(R.id.negative);
        messageStartMargin = rootView.findViewById(R.id.messageStartMargin);
        messageTopMargin = rootView.findViewById(R.id.messageTopMargin);
        messageEndMargin = rootView.findViewById(R.id.messageEndMargin);
        messageBottomMargin = rootView.findViewById(R.id.messageBottomMargin);

        rootView.setOnClickListener(v -> {
            if (cancelable) {
                dismiss();
            }
        });
        messageStartMargin.setOnClickListener(v -> rootView.callOnClick());
        messageTopMargin.setOnClickListener(v -> rootView.callOnClick());
        messageEndMargin.setOnClickListener(v -> rootView.callOnClick());
        messageBottomMargin.setOnClickListener(v -> rootView.callOnClick());

        addView(rootView);
    }

    public MessageDialogView setPositiveButton(int stringRes, View.OnClickListener listener) {
        positive.setText(stringRes)
                .setOnClickListener(v -> {
                    dismiss();

                    if (listener != null) {
                        listener.onClick(v);
                    }
                });

        return this;
    }

    public MessageDialogView setNegativeButton(int stringRes, View.OnClickListener listener) {
        negative.setText(stringRes)
                .setOnClickListener(v -> {
                    dismiss();

                    if (listener != null) {
                        listener.onClick(v);
                    }
                });

        return this;
    }

    public MessageDialogView setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
        return this;
    }

    public MessageDialogView setMessage(int messageTextRes) {
        message.setText(messageTextRes);
        return this;
    }

    public MessageDialogView setMessage(String messageText) {
        message.setText(messageText);
        return this;
    }

    public MessageDialogView show() {
        if (!negative.hasText()) {
            negative.removeFrame()
                    .removeClickAnimation();
        }

        return super.show();
    }
}
