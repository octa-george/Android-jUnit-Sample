package ro.octa.junit.medium.preferences;

import android.content.SharedPreferences;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ro.octa.mockito.sample.preferences.Preferences;
import ro.octa.mockito.sample.preferences.SharedPreferencesHelper;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;


/**
 * Unit tests for the {@link SharedPreferencesHelper} that mocks {@link android.content.SharedPreferences}.
 *
 * @author Octavian Cimpu
 */
@RunWith(MockitoJUnitRunner.class)
public class SharedPreferencesHelperTest {

    private static final String TOKEN = "WhAtzzUpp123421";
    private static final boolean IS_TIMER_EXPIRED = false;
    private static final long INSTALLATION_TIME = 12345L;

    private SharedPreferencesHelper mMockSharedPreferencesHelper;
    private SharedPreferencesHelper mMockBrokenSharedPreferencesHelper;

    @Mock SharedPreferences mMockSharedPreferences;
    @Mock SharedPreferences mMockBrokenSharedPreferences;
    @Mock SharedPreferences.Editor mMockEditor;
    @Mock SharedPreferences.Editor mMockBrokenEditor;

    @Before
    public void iniMocks() {
        this.mMockSharedPreferencesHelper = createMockSharedPreference();
        this.mMockBrokenSharedPreferencesHelper = createBrokenMockSharedPreference();
    }

    @Test
    public void sharedPreferencesHelper_SaveAndReadToken() {
        // Save the token to SharedPreferences
        boolean success = mMockSharedPreferencesHelper.setToken(TOKEN);

        assertThat("Checking that save... returns true", success, is(true));

        // Read token from SharedPreferences
        String token = mMockSharedPreferencesHelper.getToken();

        // Make sure both written and retrieved personal information are equal.
        assertThat("Checking that the token has been persisted and read correctly",
                token, is(equalTo(TOKEN)));
    }

    @Test
    public void sharedPreferencesHelper_SaveTokenFailed_ReturnsFalse() {
        // Read token from a broken SharedPreferencesHelper
        boolean success = mMockBrokenSharedPreferencesHelper.setToken(TOKEN);
        assertThat("Makes sure writing to a broken SharedPreferencesHelper returns false", success, is(false));
    }

    @Test
    public void sharedPreferencesHelper_SaveAndReadTimerExpired() {
        // Save the time expired flag to SharedPreferences
        boolean success = mMockSharedPreferencesHelper.setTimerExpired(IS_TIMER_EXPIRED);

        assertThat("Checking that save... returns true", success, is(true));

        // Read is timer expired flag from SharedPreferences
        boolean isTimerExpired = mMockSharedPreferencesHelper.isTimerExpired();

        // Make sure both written and retrieved personal information are equal.
        assertThat("Checking that the is timer expired flag has been persisted and read correctly",
                isTimerExpired, is(equalTo(IS_TIMER_EXPIRED)));
    }

    @Test
    public void sharedPreferencesHelper_SaveTimerExpiredFailed_ReturnsFalse() {
        // Read timer expired flag from a broken SharedPreferencesHelper
        boolean success = mMockBrokenSharedPreferencesHelper.setTimerExpired(IS_TIMER_EXPIRED);
        assertThat("Makes sure writing to a broken SharedPreferencesHelper returns false", success, is(false));
    }

    @Test
    public void sharedPreferencesHelper_SaveInstallationTime() {
        when(mMockSharedPreferences.getLong(eq(Preferences.PrefKeys.INSTALLATION_TIME.name()),
                anyLong())).thenReturn(-1L);

        // Save the time expired flag to SharedPreferences
        boolean success = mMockSharedPreferencesHelper.setInstallationTime(INSTALLATION_TIME);

        assertThat("Checking that save... returns true", success, is(true));

        when(mMockSharedPreferences.getLong(eq(Preferences.PrefKeys.INSTALLATION_TIME.name()),
                anyLong())).thenReturn(INSTALLATION_TIME);

        // Read is timer expired flag from SharedPreferences
        long installationTime = mMockSharedPreferencesHelper.getInstallationTime();

        // Make sure both written and retrieved personal information are equal.
        assertThat("Checking that the installation timestamp has been persisted and read correctly",
                installationTime, is(equalTo(INSTALLATION_TIME)));

        boolean failure = mMockSharedPreferencesHelper.setInstallationTime(INSTALLATION_TIME);
        // the install time has already been saved so we shouldn't save it again
        assertThat("Checking that save... returns false", failure, is(false));
    }

    @Test
    public void sharedPreferencesHelper_SaveInstallationTimeFailed_ReturnsFalse() {
        // Read timer expired flag from a broken SharedPreferencesHelper
        boolean success = mMockBrokenSharedPreferencesHelper.setInstallationTime(INSTALLATION_TIME);
        assertThat("Makes sure writing to a broken SharedPreferencesHelper returns false", success, is(false));
    }

    /**
     * Creates a mocked SharedPreferences.
     */
    private SharedPreferencesHelper createMockSharedPreference() {
        // Mocking reading the SharedPreferences as if mMockSharedPreferences was previously written
        // correctly.
        when(mMockSharedPreferences.getString(eq(Preferences.PrefKeys.TOKEN.name()),
                anyString())).thenReturn(TOKEN);
        when(mMockSharedPreferences.getBoolean(eq(Preferences.PrefKeys.IS_TIMER_EXPIRED.name()),
                anyBoolean())).thenReturn(IS_TIMER_EXPIRED);

        // Mocking a successful commit.
        when(mMockEditor.commit()).thenReturn(true);

        // Return the MockEditor when requesting it.
        when(mMockSharedPreferences.edit()).thenReturn(mMockEditor);
        return new SharedPreferencesHelper(mMockSharedPreferences);
    }

    /**
     * Creates a mocked SharedPreferences that fails when writing.
     */
    private SharedPreferencesHelper createBrokenMockSharedPreference() {
        // Mocking a commit that fails.
        when(mMockBrokenEditor.commit()).thenReturn(false);

        // Return the broken MockEditor when requesting it.
        when(mMockBrokenSharedPreferences.edit()).thenReturn(mMockBrokenEditor);
        return new SharedPreferencesHelper(mMockBrokenSharedPreferences);
    }

}

