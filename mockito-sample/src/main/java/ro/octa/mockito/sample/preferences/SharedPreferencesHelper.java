package ro.octa.mockito.sample.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import ro.octa.mockito.sample.MyApplication;

/**
 * Helper class to manage access to {@link SharedPreferences}.
 * <p/>
 * Created on 7/23/16.
 */
public class SharedPreferencesHelper {

    private static SharedPreferencesHelper instance;

    // The injected SharedPreferences implementation to use for persistence
    private SharedPreferences sharedPreferences;

    /**
     * Constructor with dependency injection.
     *
     * @param preferences The {@link SharedPreferences} that will be used in this DAO.
     */
    public SharedPreferencesHelper(SharedPreferences preferences) {
        this.sharedPreferences = preferences;
    }

    /**
     * Retrieves the {@link SharedPreferencesHelper} containing the singleton instance
     * of this class
     *
     * @return the Retrieved instance of {@link SharedPreferencesHelper}
     */
    public static SharedPreferencesHelper getInstance() {
        if (instance == null) {
            instance = new SharedPreferencesHelper(getPrefs());
        }
        return instance;
    }

    /**
     * Retrieves the {@link SharedPreferences} containing the default Prefs settings.
     *
     * @return the Retrieved instance of {@link SharedPreferences}
     */
    private static SharedPreferences getPrefs() {
        return MyApplication.getInstance().getSharedPreferences(
                Preferences.PrefNames.PREFS_NAME_TAG, Context.MODE_PRIVATE);
    }

    /**
     * Retrieves the {@link Boolean} containing the flag for the countdown timer from
     * {@link SharedPreferences}.
     *
     * @return the Retrieved {@link Boolean}.
     */
    public boolean isTimerExpired() {
        return sharedPreferences.getBoolean(Preferences.PrefKeys.IS_TIMER_EXPIRED.name(), false);
    }

    /**
     * Saves the given {@link Boolean} containing the expired flag to {@link SharedPreferences}.
     *
     * @param isExpired contains data to save to {@link SharedPreferences}.
     * @return @{code true} if writing to {@link SharedPreferences} succeeded. @{code false}
     * otherwise.
     */
    public boolean setTimerExpired(boolean isExpired) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(Preferences.PrefKeys.IS_TIMER_EXPIRED.name(), isExpired);
        return editor.commit();
    }

    /**
     * Retrieves the {@link String} containing the token from
     * {@link SharedPreferences}.
     *
     * @return the Retrieved {@link String}.
     */
    public String getToken() {
        return sharedPreferences.getString(Preferences.PrefKeys.TOKEN.name(), "");
    }

    /**
     * Saves the given {@link String} containing the token to {@link SharedPreferences}.
     *
     * @param token contains data to save to {@link SharedPreferences}.
     * @return @{code true} if writing to {@link SharedPreferences} succeeded. @{code false}
     * otherwise.
     */
    public boolean setToken(String token) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Preferences.PrefKeys.TOKEN.name(), token);
        return editor.commit();
    }

    /**
     * Retrieves the {@link Long} containing the timestamp of the app installation time from
     * {@link SharedPreferences}.
     *
     * @return the Retrieved {@link Long}.
     */
    public long getInstallationTime() {
        return sharedPreferences.getLong(Preferences.PrefKeys.INSTALLATION_TIME.name(), -1L);
    }

    /**
     * Saves the given {@link Long} containing the timestamp for the first launch to {@link SharedPreferences}.
     *
     * @param timestamp contains data to save to {@link SharedPreferences}.
     * @return @{code true} if writing to {@link SharedPreferences} succeeded. @{code false}
     * if failed or if it already exits.
     */
    public boolean setInstallationTime(long timestamp) {
        if (getInstallationTime() > -1L) {
            return false;
        } else {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putLong(Preferences.PrefKeys.INSTALLATION_TIME.name(), timestamp);
            return editor.commit();
        }
    }
}
