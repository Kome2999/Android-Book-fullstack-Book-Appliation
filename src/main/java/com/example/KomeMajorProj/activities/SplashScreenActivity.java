package com.example.KomeMajorProj.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.KomeMajorProj.R;

public class SplashScreenActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        firebaseAuth = FirebaseAuth.getInstance();

        //start main screen after 2seconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkUser();
            }
        },2000);//2000 means 2 seconds
    }

    private void checkUser() {

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser == null){

            startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
            finish();
        }
        else {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
            ref.child(firebaseUser.getUid())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {

                            String userType = ""+snapshot.child("userType").getValue();

                            if (userType.equals("user")){

                                startActivity(new Intent(SplashScreenActivity.this, DashUserActivity.class));
                                finish();
                            }
                            else if (userType.equals("admin")){

                                startActivity(new Intent(SplashScreenActivity.this, DashAdminActivity.class));
                                finish();
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError error) {

                        }
                    });

        }
    }
}

