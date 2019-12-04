package pl.tamides.ytube.views;

import android.view.LayoutInflater;
import android.widget.ProgressBar;

import androidx.constraintlayout.widget.ConstraintLayout;

import pl.tamides.ytube.App;
import pl.tamides.ytube.R;

public class ProgressDialogView extends BaseDialogView<ProgressDialogView> {

    private NormalTextView message;
    private ProgressBar progress;

    public ProgressDialogView() {
        super(App.getCurrentActivity());

        ConstraintLayout rootView = (ConstraintLayout) LayoutInflater.from(App.getCurrentActivity()).inflate(R.layout.view_progress_dialog, activityContainer, false);
        message = rootView.findViewById(R.id.message);
        progress = rootView.findViewById(R.id.progress);

        addView(rootView);
    }

    public ProgressDialogView setMessage(int messageTextRes) {
        message.setText(messageTextRes);
        return this;
    }

    public ProgressDialogView setMessage(String messageText) {
        message.setText(messageText);
        return this;
    }

    public ProgressDialogView setProgress(int progress) {
        this.progress.setProgress(progress);
        return this;
    }
}
