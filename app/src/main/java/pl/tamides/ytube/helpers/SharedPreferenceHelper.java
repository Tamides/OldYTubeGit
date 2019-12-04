package pl.tamides.ytube.helpers;

import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import pl.tamides.ytube.App;

public class SharedPreferenceHelper {

    private static volatile SharedPreferenceHelper instance = null;

    private SharedPreferences sharedPreferences;

    public SharedPreferenceHelper() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(App.getAppContext());
    }

    public static SharedPreferenceHelper getInstance() {
        if (instance == null) {
            synchronized (SharedPreferenceHelper.class) {
                if (instance == null) {
                    instance = new SharedPreferenceHelper();
                }
            }
        }

        return instance;
    }

    public void putString(Key key, String value) {
        sharedPreferences.edit().putString(key.getValue(), value).apply();
    }

    public String getString(Key key, String defautValue) {
        return sharedPreferences.getString(key.getValue(), defautValue);
    }

    public enum Key {
        AUTH_STATE("AuthState");

        private String value;

        Key(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
