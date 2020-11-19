package com.group11.cmpt276_project.service.repository;

//Interface describing what functions the Preference Repository should have
public interface IPreferenceRepository {

    String getPreference(String preference);
    void savePreference(String preference, String value);
}
