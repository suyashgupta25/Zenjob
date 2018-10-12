package com.zenjob.data.local

import android.content.SharedPreferences
import com.zenjob.data.local.PreferenceHelper.KEY_AUTH_TOKEN
import com.zenjob.data.local.PreferenceHelper.KEY_EXPIRY_TIME
import com.zenjob.data.local.PreferenceHelper.KEY_REFRESH_TOKEN
import com.zenjob.data.local.PreferenceHelper.KEY_TOKEN_TYPE
import com.zenjob.data.local.PreferenceHelper.KEY_USERNAME
import com.zenjob.data.local.PreferenceHelper.get
import com.zenjob.data.local.PreferenceHelper.set
import com.zenjob.data.model.LoginResponse
import com.zenjob.utils.AppConstants.Companion.EMPTY
import javax.inject.Inject

class AppPrefsHelper @Inject constructor(private val prefs: SharedPreferences) : PrefsHelper {

    override fun saveUserAuth(response: LoginResponse) {
        prefs[KEY_AUTH_TOKEN] = response.access_token
        prefs[KEY_REFRESH_TOKEN] = response.refresh_token
        prefs[KEY_EXPIRY_TIME] = response.expires_in
        prefs[KEY_TOKEN_TYPE] = response.token_type
        prefs[KEY_USERNAME] = response.username
    }

    override fun checkForSessionAvailable(): Boolean {
        return !prefs[KEY_AUTH_TOKEN, EMPTY].isNullOrEmpty()
    }

}