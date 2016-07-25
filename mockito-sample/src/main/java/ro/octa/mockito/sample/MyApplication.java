package ro.octa.mockito.sample;

import android.app.Application;

import ro.octa.mockito.sample.preferences.SharedPreferencesHelper;

/**
 * Created on 7/23/16.
 */
public class MyApplication extends Application {

    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        SharedPreferencesHelper.getInstance().setInstallationTime(System.currentTimeMillis());
    }

    public static MyApplication getInstance() {
        return instance;
    }
}
