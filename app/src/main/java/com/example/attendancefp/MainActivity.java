package com.example.attendancefp;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.attendancefp.databinding.ActivityMainBinding;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;


    private String txtEmail, txtPassword, txtName;  // for register
    private FirebaseAuth mAuth;

    private FirebaseUser mUser;  //for login

    private DatabaseReference mReference;
    private HashMap<String,Object> mData;   //verileri kaydetmek için kullanacağımız map


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,R.id.nav_about,R.id.nav_logout)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        //////// our Methods

        mAuth = FirebaseAuth.getInstance();
        register();
        //mUser = mAuth.getCurrentUser(); // aynı kullanıcıyı sonraki girişlerde hatırlamamıza yarar
        login();

        //mreference initialize
        mReference = FirebaseDatabase.getInstance().getReference();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    //////////// our Methods


    //Register sayfası varmış gib kayıt alır
    public void register()
    {
        txtEmail="mustafaayerdem@posta.mu.edu.tr";
        txtPassword ="123mustafa";
        txtName ="Mustafa";

        if(!TextUtils.isEmpty(txtName) && !TextUtils.isEmpty(txtEmail) && !TextUtils.isEmpty((txtPassword)))
        {

            mAuth.createUserWithEmailAndPassword(txtEmail, txtPassword)
                    //olayın hatalarına dair bilgiler döner buradan
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                mUser = mAuth.getCurrentUser();

                                //Toast.makeText( MainActivity.this, "Register is succesfully", Toast.LENGTH_SHORT).show();
                                // data can store with key and value
                                mData =new HashMap<>();
                                mData.put("userName", txtName);
                                mData.put("userEmail",txtEmail);
                                mData.put("userPassword", txtPassword);
                                mData.put("userId", mUser.getUid());

                                mReference.child("Users").child(mUser.getUid())   //users altında uid yi bulup ona ekliyor
                                        .setValue(mData)
                                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful())
                                                    Toast.makeText(MainActivity.this, "Register is succesfuly", Toast.LENGTH_SHORT).show();
                                                else
                                                    Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });  //users altına mData ları eklememize yarıyor bu setValue
                            }
                            else {
                                Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }else
        {
            Toast.makeText(this, "Email and password can not be null!", Toast.LENGTH_SHORT).show();
        }
    }

    public void login()
    {
        txtEmail="mustafaayerdem@posta.mu.edu.tr";
        txtPassword ="123mustafa";

        if(!TextUtils.isEmpty(txtEmail) && !TextUtils.isEmpty((txtPassword)))
        {
            mAuth.signInWithEmailAndPassword(txtEmail, txtPassword)
                    .addOnSuccessListener(this, new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            mUser = mAuth.getCurrentUser();
                            System.out.println("Kullanıcı Adı : "+ mUser.getDisplayName());
                            System.out.println("Kullanıcı Email : "+ mUser.getEmail());
                            System.out.println("Kullanıcı Uid : "+ mUser.getUid());
                        }
                    }).addOnFailureListener(this, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }else{
            Toast.makeText(this, "Email and  can not be null!", Toast.LENGTH_SHORT).show();
        }
    }
}