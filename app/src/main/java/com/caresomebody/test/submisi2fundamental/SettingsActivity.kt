package com.caresomebody.test.submisi2fundamental

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import com.caresomebody.test.submisi2fundamental.databinding.ActivitySettingsBinding
import com.caresomebody.test.submisi2fundamental.preference.ReminderPref
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivitySettingsBinding
    private lateinit var alarmReceiver: AlarmReceiver
    private lateinit var reminder: Reminder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val reminderPref = ReminderPref(this)
        binding.switchBtn.isChecked = reminderPref.getReminder().isReminded
        alarmReceiver = AlarmReceiver()

        binding.switchBtn.setOnCheckedChangeListener{
            _, isChecked ->
            if (isChecked){
                alarmReminder(true)
                alarmReceiver.setRepeatingAlarm(this,getString(R.string.alarm_type), getString(R.string.time_alarm), getString(R.string.notif_message))
            } else {
                alarmReminder(false)
                alarmReceiver.cancelAlarm(this)
            }
        }
        binding.btnSetLanguage.setOnClickListener(this)
    }

    private fun alarmReminder(state: Boolean){
        val reminderPref = ReminderPref(this)
        reminder = Reminder()
        reminder.isReminded = state
        reminderPref.setReminder(reminder)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btn_set_language -> {
                val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(mIntent)
            }
        }
    }
}