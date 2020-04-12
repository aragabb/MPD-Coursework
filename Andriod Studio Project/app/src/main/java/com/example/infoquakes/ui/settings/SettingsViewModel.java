//Ahmed Ragab Badawy- S1804193
package com.example.infoquakes.ui.settings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SettingsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SettingsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is show settings");
    }

    public LiveData<String> getText() {
        return mText;
    }
}