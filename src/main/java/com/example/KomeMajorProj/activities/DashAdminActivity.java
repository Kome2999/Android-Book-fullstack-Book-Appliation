package com.example.KomeMajorProj.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.KomeMajorProj.adapters.AdaptCategory;
import com.example.KomeMajorProj.databinding.ActivityDashboardAdminBinding;
import com.example.KomeMajorProj.models.ModelCategory;

import java.util.ArrayList;

public class DashAdminActivity extends AppCompatActivity {


    private ActivityDashboardAdminBinding binding;


    private FirebaseAuth firebaseAuth;


    private ArrayList<ModelCategory> categoryArrayList;

    private AdaptCategory adaptCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();
        checkUser();
        loadCategories();



        binding.searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                try {
                    adaptCategory.getFilter().filter(s);
                }
                catch (Exception e){

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                checkUser();
            }
        });

        binding.addCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashAdminActivity.this, CatAddActivity.class));
            }
        });

        binding.addPdfFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashAdminActivity.this, PdfAddActivity.class));
            }
        });

        binding.profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashAdminActivity.this, ProfileActivity.class));
            }
        });


    }

    private void loadCategories() {

        categoryArrayList = new ArrayList<>();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Categories");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                categoryArrayList.clear();
                for (DataSnapshot ds: snapshot.getChildren()){

                    ModelCategory model = ds.getValue(ModelCategory.class);

                    categoryArrayList.add(model);
                }

                adaptCategory = new AdaptCategory(DashAdminActivity.this, categoryArrayList);

                binding.categoriesRv.setAdapter(adaptCategory);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void checkUser() {

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser==null){

            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        else {

            String email = firebaseUser.getEmail();

            binding.subTitleTv.setText(email);
        }
    }
}