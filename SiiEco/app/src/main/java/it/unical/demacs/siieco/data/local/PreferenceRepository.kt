package it.unical.demacs.siieco.data.local

import android.content.Context
import android.content.SharedPreferences
import it.unical.demacs.siieco.common.Constants

class PreferenceRepository(
    private val context: Context
) {

    private val pref: SharedPreferences =
        context.getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE)
    private val editor = pref.edit()

    private fun String.put(long: Long) {
        editor.putLong(this, long)
        editor.commit()
    }

    private fun String.put(int: Int) {
        editor.putInt(this, int)
        editor.commit()
    }

    private fun String.put(string: String) {
        editor.putString(this, string)
        editor.commit()
    }

    private fun String.put(boolean: Boolean) {
        editor.putBoolean(this, boolean)
        editor.commit()
    }

    private fun String.getLong() = pref.getLong(this, 0)

    private fun String.getInt() = pref.getInt(this, 0)

    private fun String.getString() = pref.getString(this, "")!!

    private fun String.getBoolean() = pref.getBoolean(this, false)

    fun putEmail(email: String) {
        Constants.PREF_EMAIL.put(email)
    }

    fun getEmail() = Constants.PREF_EMAIL.getString()

    fun clearData() {
        editor.clear()
        editor.commit()
    }

    fun putTypeUser(typeUser: String) {
        Constants.PREF_TYPE_USER.put(typeUser)
    }

    fun getTypeUser() = Constants.PREF_TYPE_USER.getString()

    /*
    fun setLoggedIn(isLoggedIn: Boolean) {

    }

    fun setLoggedIn() = PREF_LOGGED_IN.getBoolean()

    fun setShareMsg(msg: String) {
        PREF_SHARE_MESSAGE.put(msg)
    }

    fun getShareMsg() = PREF_SHARE_MESSAGE.getString()

    fun setMinimumAppVersion(version: Long) {
        PREF_MINIMUM_APP_VERSION.put(version)
    }

    fun getMinimumAppVersion() = PREF_MINIMUM_APP_VERSION.getLong()

    fun setLastRefreshTime(date: Date) {
        PREF_LAST_REFRESH_TIME.put(gson.toJson(date))
    }

    fun getLastRefreshTime(): Date? {
        PREF_LAST_REFRESH_TIME.getString().also {
            return if (it.isNotEmpty())
                gson.fromJson(PREF_LAST_REFRESH_TIME.getString(), Date::class.java)
            else
                null
        }
    }


*/
}
