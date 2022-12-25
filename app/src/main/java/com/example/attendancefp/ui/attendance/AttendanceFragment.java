package com.example.attendancefp.ui.attendance;

import static com.example.attendancefp.MainActivity.mAuth;
import static com.example.attendancefp.MainActivity.mUser;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.attendancefp.MainActivity;
import com.example.attendancefp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AttendanceFragment extends Fragment {

    private DatabaseReference mmReference;
    //private FirebaseUser mUser;
    //private FirebaseAuth mAuth;

    public AttendanceFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //mUser = mAuth.getCurrentUser();
        //mmReference=FirebaseDatabase.getInstance().getReference("Users").child(mUser.getUid());

        ListLectureAttendance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_attendance, container, false);
    }

    public void ListLectureAttendance()
    {

        mUser = mAuth.getCurrentUser(); //giriş yapan kullanıcının bilgilerini alıyoruz bu işlemle

        //mReference = FirebaseDatabase.getInstance().getReference(); // firebase deki root bölümü alır .
        mmReference=FirebaseDatabase.getInstance().getReference("Users").child(mUser.getUid()).child("Lectures");
        mmReference.addValueEventListener(new ValueEventListener() {  //kullanıcının bilgisi değişirse bile tetiklenir
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {  //dönen veri datasnapshot şeklinde döner
                System.out.println(snapshot.getValue());
                for(DataSnapshot snp: snapshot.getChildren())
                {
                    System.out.println(snp.getChildren());
                }

            }

            //herhangi bir hata vs gerçekleşirse çalışacak olan kod
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show(); //içinde bulunudğumuz fragmnet a this ile değilde getactivity ile ulaşabiliyoruz
            }
        });

    }
}