package com.dicoding.courseschedule.ui.setting

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.notification.DailyReminder
import com.dicoding.courseschedule.ui.home.HomeActivity
import com.dicoding.courseschedule.util.NightMode
import java.util.Locale

class SettingsFragment : PreferenceFragmentCompat() {

    private lateinit var themePreference: ListPreference
    private lateinit var notificationSwitchPreference: SwitchPreference

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        // TODO 10 : Update theme based on value in ListPreference
        // TODO 11 : Schedule and cancel notification in DailyReminder based on SwitchPreference

        themePreference =
            findPreference<ListPreference>(getString(R.string.pref_key_dark)) as ListPreference
        notificationSwitchPreference =
            findPreference<SwitchPreference>(getString(R.string.pref_key_notify)) as SwitchPreference

        themePreference.setOnPreferenceChangeListener { _, newValue ->
            val mode = NightMode.valueOf(newValue.toString().uppercase(Locale.ROOT))
            updateTheme(mode.value)
            true
        }

        notificationSwitchPreference.setOnPreferenceChangeListener { _, newValue ->
            val enableNotification = newValue as Boolean
            val dailyReminder = DailyReminder()
            if (enableNotification) {
                context?.let {
                    dailyReminder.setDailyReminder(it)
                }
            } else {
                context?.let {
                    dailyReminder.cancelAlarm(it)
                }
            }
            true
        }
    }

    private fun updateTheme(nightMode: Int): Boolean {
        AppCompatDelegate.setDefaultNightMode(nightMode)
        requireActivity().recreate()
        return true
    }

}