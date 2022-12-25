package com.example.attendancefp.ui.lectures;

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

import java.util.ArrayList;
import java.util.Objects;


public class LectureSchedule extends Fragment implements AdapterView.OnItemClickListener {

    ArrayList<String> Lectures = new ArrayList<String>(); ;
    Button btn;
    ListView listView ;
    ArrayAdapter<String> adapter;
    public LectureSchedule() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lecture_schedule, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn = (Button) requireView().findViewById(R.id.btton);

        Lectures.add("web");
        listView =(ListView)view.findViewById(R.id.lst);
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,Lectures);



        btn.setOnClickListener(this::onClick);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if(i==0){
            Toast.makeText(getActivity(),"NLP", Toast.LENGTH_SHORT).show();
        }

    }


    public void onClick(View v) {
        Lectures.add("NLP"+"                   "+"Monday 12:00 - 14:50");

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

    }



}