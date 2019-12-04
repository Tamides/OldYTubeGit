package pl.tamides.ytube;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class BaseActivity extends AppCompatActivity {

    private volatile List<Runnable> tasksToExecute = new ArrayList<>();
    private volatile boolean stopped = false;
    private Thread taskManager;
    private volatile Thread taskExecutor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.setCurrentActivity(this);
    }

    public void onBg(Runnable runnable) {
        getTaskDetailsOrAddTask(runnable, false);
        executeTasks();
    }

    public void onUi(Runnable runnable) {
        if (!stopped) {
            runOnUiThread(runnable);
        }
    }

    private synchronized TaskDetails getTaskDetailsOrAddTask(Runnable task, boolean returnTasksSize) {
        if (returnTasksSize) {
            return new TaskDetails().setTaskCount(tasksToExecute.size());
        }

        if (task != null) {
            tasksToExecute.add(task);
            return null;
        }

        if (tasksToExecute.size() == 0) {
            return null;
        }

        Runnable taskToExecute = tasksToExecute.get(0);
        tasksToExecute.remove(0);

        return new TaskDetails().setRunnable(taskToExecute);
    }

    public void executeTasks() {
        try {
            if (taskManager != null) {
                return;
            }

            taskManager = new Thread(() -> {
                try {
                    TaskDetails taskDetails = getTaskDetailsOrAddTask(null, false);

                    while (taskDetails != null) {
                        if (taskExecutor != null) {
                            taskExecutor.join();
                        }

                        taskExecutor = new Thread(taskDetails.getRunnable());
                        taskExecutor.start();

                        taskDetails = getTaskDetailsOrAddTask(null, false);
                    }

                    onUi(() -> {
                        taskManager = null;

                        if (getTaskDetailsOrAddTask(null, true).getTaskCount() > 0) {
                            executeTasks();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    App.showMessage(e.getMessage());
                }
            });
            taskManager.start();
        } catch (Exception e) {
            e.printStackTrace();
            App.showMessage(e.getMessage());
        }
    }

    @Override
    protected void onStop() {
        stopped = true;
        super.onStop();
    }

    private class TaskDetails {

        private Runnable runnable;
        private int taskCount;

        public Runnable getRunnable() {
            return runnable;
        }

        public TaskDetails setRunnable(Runnable runnable) {
            this.runnable = runnable;
            return this;
        }

        public int getTaskCount() {
            return taskCount;
        }

        public TaskDetails setTaskCount(int taskCount) {
            this.taskCount = taskCount;
            return this;
        }
    }
}
