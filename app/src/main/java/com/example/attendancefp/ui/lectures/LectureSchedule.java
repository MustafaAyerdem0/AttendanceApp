package com.example.attendancefp.ui.lectures;

import static com.example.attendancefp.MainActivity.mAuth;
import static com.example.attendancefp.MainActivity.mUser;

import android.app.ListActivity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.attendancefp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

//MUSTAFA AYERDEM
public class LectureSchedule extends Fragment  {

    private DatabaseReference mReference;

    ArrayList<String> Lectures = new ArrayList<String>(); ;
    Button btn;
    ListView listView ;
    ArrayAdapter adapter;
    public LectureSchedule() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lecture_schedule, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView =(ListView)view.findViewById(R.id.lst);
        adapter = new ArrayAdapter<String>(getContext(), R.layout.lectures_schedule_text_setting_layout,Lectures);
        ListSchedule();


    }



    public void ListSchedule()
    {

        mUser = mAuth.getCurrentUser(); //giriş yapan kullanıcının bilgilerini alıyoruz bu işlemle

        //mReference = FirebaseDatabase.getInstance().getReference(); // firebase deki root bölümü alır .
        mReference= FirebaseDatabase.getInstance().getReference("Users").child(mUser.getUid()).child("LectureSchedule");
        mReference.addValueEventListener(new ValueEventListener() {  //kullanıcının bilgisi değişirse bile tetiklenir
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {  //dönen veri datasnapshot şeklinde döner
                System.out.println(snapshot.getValue());
                for(DataSnapshot snp: snapshot.getChildren())
                {
                    Lectures.add(snp.getKey()+ "\n\n" +snp.getValue());

                }

                listView.setAdapter(adapter);

            }

            //herhangi bir hata vs gerçekleşirse çalışacak olan kod
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show(); //içinde bulunudğumuz fragmnet a this ile değilde getactivity ile ulaşabiliyoruz
            }
        });

    }

}