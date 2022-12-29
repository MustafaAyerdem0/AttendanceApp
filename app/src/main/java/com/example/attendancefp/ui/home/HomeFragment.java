package com.example.attendancefp.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.attendancefp.MainActivity;
import com.example.attendancefp.R;
import com.example.attendancefp.databinding.FragmentHomeBinding;
import com.journeyapps.barcodescanner.CaptureActivity;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button btn_scan = view.findViewById(R.id.btn_scanQr);
        btn_scan.setOnClickListener(view1 ->{
            scanCode();
        });

        Button btn_attendance = view.findViewById(R.id.btn_attendance);
        btn_attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToAttendance(view);
            }
        });
        Button btn_lectureschedule = view.findViewById(R.id.btn_lectureschedule);
        btn_lectureschedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToLectureSchedule(view);
            }
        });
    }

    private void scanCode() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Volume up to flask on");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class); //CaptureAct class we need to use
        barLaucher.launch(options);
    }

    ActivityResultLauncher<ScanOptions> barLaucher = registerForActivityResult(new ScanContract(),result ->
    {
        if(result.getContents() != null)
        {
            AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());//içinde bulunduğumuz activity ye ulaşmamız gerek
            builder.setTitle("Result");
            builder.setMessage(result.getContents());
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).show();
        }
    });

    public void goToAttendance(View view){
        NavDirections action = HomeFragmentDirections.actionNavHomeToAttendanceFragment();
        Navigation.findNavController(view).navigate(action);
    }
    public void goToLectureSchedule(View view){
        NavDirections action = HomeFragmentDirections.actionNavHomeToLectureSchedule();
        Navigation.findNavController(view).navigate(action);
    }











}