package com.group11.cmpt276_project.service.repository;

public interface IPreferenceRepository {

    String getPreference(String preference);
    void savePreference(String preference, String value);
}
