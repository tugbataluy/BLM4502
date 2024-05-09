package com.example.addictionrecovery;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class TechnologyAddictionSupportPage extends AppCompatActivity {

    RelativeLayout relativeLayout;
    FirebaseAuth auth;

    Toolbar tb;
    ListView listView;
    String [] ngos;
    ImageView navIcon;
    NavigationView navigationView;

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technology_addiction_support_page);

        relativeLayout=(RelativeLayout) findViewById(R.id.technology_addiction_support_layout);
        auth= FirebaseAuth.getInstance();

        toolBarArrangement();
        showSupportList();
        drawerInitialization();
        navBottomArrangements();
        relativeLayoutClickerEnable();
    }

    public void toolBarArrangement(){
        tb=(Toolbar) findViewById(R.id.toolbar);
        tb.setTitle("Teknoloji Bağımlılığı");
    }

    public void showSupportList(){
        ngos= getResources().getStringArray(R.array.addiction_support_organizations);

        listView=(ListView) findViewById(R.id.ngo_list_view);
        ArrayAdapter<String> listAdapter= new ArrayAdapter<String>(TechnologyAddictionSupportPage.this,R.layout.tech_addiction_support_list_item,ngos);
        listView.setAdapter(listAdapter);
    }

    public void getUserName(){
        TextView user_name;
        FirebaseFirestore db;
        DocumentReference docRef  ;
        FirebaseUser user;

        user_name=findViewById(R.id.user_name);
        db = FirebaseFirestore.getInstance();
        auth= FirebaseAuth.getInstance();
        user= auth.getCurrentUser();
        docRef = db.collection("users").document(user.getUid());

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String name = document.getString("Name");
                        if (name != null) {
                            // TextView'e atama işlemi
                            Log.d("drawer_menu", "Name alanı not null.");
                            user_name.setText(name);
                        } else {
                            Log.d("drawer_menu", "Name alanı null.");
                        }

                    } else {
                        Log.d("drawer_menu", "Doküman bulunamadı");
                    }
                } else {
                    Log.d("drawer_menu", "Belge alınamadı: ", task.getException());
                }
            }
        });
    }

    public void drawerInitialization(){
        navigationView=(NavigationView) findViewById(R.id.nav_view);
        navigationView.setVisibility(View.INVISIBLE);

        navIcon=(ImageView) findViewById(R.id.navigation_icon);
        navIcon.setOnClickListener( new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(navigationView.getVisibility()==View.INVISIBLE){
                    getUserName();
                    navigationView.setVisibility(View.VISIBLE);
                }
                else{
                    navigationView.setVisibility(View.INVISIBLE);
                }
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.kullanici_profili_option:
                        Intent intent= new Intent(TechnologyAddictionSupportPage.this,user_profile.class);
                        startActivity(intent);
                        break;
                    case R.id.neyi_amacliyoruz_option:
                        Intent intent1= new Intent(TechnologyAddictionSupportPage.this, Purpose.class);
                        startActivity(intent1);
                        break;
                    case R.id.geri_bildirim_option:
                        Intent intent3 = new Intent(TechnologyAddictionSupportPage.this, Feedback.class);
                        startActivity(intent3);
                        break;
                    case R.id.cikis_yap_option:
                        showLogoutConfirmationDialog();
                        break;
                    default:
                        return true;

                }
                return  false;

            }
        });
    }

    private void showLogoutConfirmationDialog() {
        // AlertDialog oluştur
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Çıkış yapmak istediğinize emin misiniz?");
        builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                logout();
            }
        });
        builder.setNegativeButton("İptal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // İptal durumu, bir şey yapmaya gerek yok
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void logout(){
        auth.signOut();
        Intent intent= new Intent(TechnologyAddictionSupportPage.this, LoginPage.class);
        startActivity(intent);
        finish();
    }

    public void navBottomArrangements(){
        bottomNavigationView=(BottomNavigationView) findViewById(R.id.bottom_nav_id);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId= item.getItemId();
                switch (itemId){
                    case R.id.home_tab:
                        System.out.println("home");
                        Intent intent= new Intent(TechnologyAddictionSupportPage.this,TechnologyAddictionMainPage.class);
                        startActivity(intent);
                        break;
                    case R.id.questions_tab:
                        System.out.println("questions");
                        Intent intent2= new Intent(TechnologyAddictionSupportPage.this,TechnologyAddictionQuestionsPage.class);
                        startActivity(intent2);
                        break;
                    case R.id.videos_tab:
                        System.out.println("videos");
                        Intent intent3= new Intent(TechnologyAddictionSupportPage.this,TechnologyAddictionVideosPage.class);
                        startActivity(intent3);
                        break;
                    case R.id.support_tab:
                        System.out.println("support");
                        Intent intent4= new Intent(TechnologyAddictionSupportPage.this,TechnologyAddictionSupportPage.class);
                        startActivity(intent4);
                        break;
                    default:

                        break;
                }

                return false;
            }
        });
    }

    public void relativeLayoutClickerEnable(){

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(navigationView.getVisibility()==View.VISIBLE){
                    navigationView.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    public void onBackPressed() {
        // Yeni aktiviteye geçiş yapmak için Intent oluştur
        super.onBackPressed();
        Intent intent = new Intent(this, TechnologyAddictionMainPage.class);
        // Yeni aktiviteyi başlat
        startActivity(intent);
        // Mevcut aktiviteyi sonlandır
        finish();
    }
}
