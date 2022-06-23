package com.example.newicqandroid;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.EditTextPreference;
import androidx.preference.PreferenceManager;
import androidx.preference.EditTextPreference;
import androidx.preference.PreferenceFragmentCompat;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.util.Log;

public class SettingActivity extends PreferenceActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.activity_setting);
        Intent intent = getIntent();
        android.preference.EditTextPreference editTextPref = (android.preference.EditTextPreference) findPreference("server");
        editTextPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                String server = editTextPref.getEditText().getText().toString();
                Intent toIntent = new Intent(getApplicationContext(), LogInActivity.class);
                toIntent.putExtra("server", server);
                setResult(RESULT_OK, toIntent);

                return true;
            }
        });
    }
}