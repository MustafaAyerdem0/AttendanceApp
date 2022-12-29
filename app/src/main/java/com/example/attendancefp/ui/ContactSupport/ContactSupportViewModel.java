package com.example.attendancefp.ui.ContactSupport;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ContactSupportViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public ContactSupportViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Contact Support fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}