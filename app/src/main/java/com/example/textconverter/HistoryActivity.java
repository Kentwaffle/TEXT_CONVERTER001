package com.example.textconverter;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
///////////////////////////Side bar imports///////////////
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;

public class HistoryActivity extends AppCompatActivity {

        private DrawerLayout drawerLayout;
        private ActionBarDrawerToggle drawerToggle;
        private ImageButton hamburgerButton;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_history);

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
                        Intent intent = new Intent(HistoryActivity.this, MainPage.class);
                        startActivity(intent);
                    } else if (id == R.id.history) {
//                        Intent intent = new Intent(HistoryActivity.this, HistoryActivity.class);
//                        startActivity(intent);
                        Toast.makeText(HistoryActivity.this, "History", Toast.LENGTH_SHORT).show();
                    }

                    DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
                    drawerLayout.closeDrawer(GravityCompat.START);
                    return true;
                }
            });
        }
}
