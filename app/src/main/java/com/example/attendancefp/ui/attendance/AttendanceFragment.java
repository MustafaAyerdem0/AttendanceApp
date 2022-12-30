package com.example.attendancefp.ui.attendance;

import static com.example.attendancefp.MainActivity.mAuth;
import static com.example.attendancefp.MainActivity.mUser;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

import java.util.ArrayList;


//TOGETHER
public class AttendanceFragment extends Fragment implements AdapterView.OnItemClickListener {

    private DatabaseReference mmReference;
    //private FirebaseUser mUser;
    //private FirebaseAuth mAuth;
    ArrayList<String> Lectures = new ArrayList<String>(); ;
    ListView listView ;
    ArrayAdapter adapter;

    public AttendanceFragment() {
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
        return inflater.inflate(R.layout.fragment_attendance, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView =(ListView)view.findViewById(R.id.lst);
        adapter = new ArrayAdapter<String>(getContext(), R.layout.lectures_schedule_text_setting_layout,Lectures);

        ListLectureAttendance();
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
                    System.out.println(snp.getKey()+ " = " +snp.getValue());
                    Lectures.add(snp.getKey()+ "\n\n" +snp.getValue());

                }

                listView.setAdapter(adapter);
                //listView.setOnItemClickListener(this);

            }


            //herhangi bir hata vs gerçekleşirse çalışacak olan kod
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show(); //içinde bulunudğumuz fragmnet a this ile değilde getactivity ile ulaşabiliyoruz
            }
        });

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if(i==0){
            Toast.makeText(getActivity(),"NLP", Toast.LENGTH_SHORT).show();
        }
    }
}