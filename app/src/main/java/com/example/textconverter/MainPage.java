package com.example.textconverter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

///////////////////////////Side bar imports///////////////
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;


public class MainPage extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ImageButton hamburgerButton;


    private static final int IMAGE_PICKER_REQUEST_CODE = 1001;

    ImageButton Cam_btn;
    Uri imageuri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_page);

        // Assign the id
        Cam_btn = findViewById(R.id.Cam_btn);

        Cam_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start Image Picker
                ImagePicker.with(MainPage.this)
                        .crop()                // Enables cropping (Optional)
                        .compress(1024)        // Compresses image to be less than 1MB (Optional)
                        .maxResultSize(1080, 1080)  // Sets max resolution to 1080x1080 (Optional)
                        .start(IMAGE_PICKER_REQUEST_CODE); // Pass the request code here
            }
        });

        //////////////////////////////Side bar Control/////////////////////////////////////3

        drawerLayout = findViewById(R.id.drawer_layout);
        hamburgerButton = findViewById(R.id.hamburgerSidebar);
//        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open_nav,R.string.close_nav);
//        drawerLayout.addDrawerListener(drawerToggle);
//        drawerToggle.syncState();

        hamburgerButton.setOnClickListener(v -> {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.home) {
//                    Intent intent = new Intent(MainPage.this, MainPage.class);
//                    startActivity(intent);
                    Toast.makeText(MainPage.this, "Home", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.history) {
                    Intent intent = new Intent(MainPage.this, HistoryActivity.class);
                    startActivity(intent);
                }

                DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_PICKER_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            Toast.makeText(this, "Image is selected", Toast.LENGTH_SHORT).show();
            imageuri = data.getData();


            Intent intent = new Intent(MainPage.this, Retake.class);
            intent.putExtra("imageUri", imageuri.toString());
            startActivity(intent);
        } else {
            Toast.makeText(this, "Image is not selected", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}


