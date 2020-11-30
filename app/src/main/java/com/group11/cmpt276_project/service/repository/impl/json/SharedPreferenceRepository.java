package com.group11.cmpt276_project.service.repository.impl.json;

import android.content.Context;
import android.content.SharedPreferences;

import com.group11.cmpt276_project.service.repository.IPreferenceRepository;

//Class to manage reading and writing from saved preferences
public class SharedPreferenceRepository implements IPreferenceRepository {

    private final SharedPreferences sharedPreferences;

    public SharedPreferenceRepository(Context context, String sharePreferenceUrl) {
        this.sharedPreferences = context.getSharedPreferences(sharePreferenceUrl, 0);
    }

    @Override
    public String getPreference(String preference) {
        return this.sharedPreferences.getString(preference, null);
    }

    @Override
    public void savePreference(String preference, String value) {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putString(preference, value);
        editor.apply();

    }
}
