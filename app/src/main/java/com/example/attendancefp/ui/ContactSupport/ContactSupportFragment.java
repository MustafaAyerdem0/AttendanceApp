package com.example.attendancefp.ui.ContactSupport;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.attendancefp.R;
import com.example.attendancefp.databinding.FragmentAboutBinding;
import com.example.attendancefp.databinding.FragmentContactSupportBinding;
import com.example.attendancefp.ui.about.AboutViewModel;


//MUSTAFA AYERDEM
public class ContactSupportFragment extends Fragment {
    private FragmentContactSupportBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ContactSupportViewModel contactSupportViewModel =
                new ViewModelProvider(this).get(ContactSupportViewModel.class);

        binding = FragmentContactSupportBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textAbout;
        contactSupportViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}