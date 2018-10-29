package com.example.andrew.uniproject;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

public class AppPreferencesFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle bundle, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }
}
