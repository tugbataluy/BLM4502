package com.example.addictionrecovery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class GeneralAddictionShowVideos extends AppCompatActivity {

    Toolbar tb;
    NavigationView navigationView;

    FirebaseAuth auth;
    RelativeLayout relativeLayout;

    ImageView navIcon;
    TextView homeIcon,questionIcon,videoIcon, helpIcon, titleView;
    YouTubePlayerView youTubePlayerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_addiction_show_videos);
        relativeLayout=(RelativeLayout)findViewById(R.id.ga_show_videos_layout);
        auth=FirebaseAuth.getInstance();



        getExtrasFromIntent();
        toolBarArrangement();
        drawerInitialization();
        relativeLayoutClickerEnable();
        navBottomArrangements();
    }
    public void toolBarArrangement(){
        tb=(Toolbar) findViewById(R.id.toolbar);
        tb.setTitle("Bağımlılık");
    }

    public void getExtrasFromIntent(){
        Bundle extras= getIntent().getExtras();
        String title= extras.getString("VIDEO_TITLE");
        String videoId= extras.getString("VIDEO_ID");
        System.out.println(title);
        System.out.println(videoId);

        titleView=(TextView) findViewById(R.id.video_title_view);
        titleView.setText(title);

        youTubePlayerView=(YouTubePlayerView) findViewById(R.id.youtube_video_player);
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {

                youTubePlayer.loadVideo(videoId, 0);
            }
        });

    }
    public void navBottomArrangements(){
        homeIcon=(TextView)findViewById(R.id.home_icon);
        questionIcon=(TextView) findViewById(R.id.questions_icon);
        videoIcon=(TextView) findViewById(R.id.videos_icon);
        helpIcon=(TextView) findViewById(R.id.help_icon);

        homeIcon.setOnClickListener(new GeneralAddictionShowVideos.BottomBarListener());
        questionIcon.setOnClickListener(new GeneralAddictionShowVideos.BottomBarListener());
        videoIcon.setOnClickListener(new GeneralAddictionShowVideos.BottomBarListener());
        helpIcon.setOnClickListener(new GeneralAddictionShowVideos.BottomBarListener());


    }
    public class BottomBarListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.home_icon:
                    Intent intent= new Intent(GeneralAddictionShowVideos.this,GeneralAddictionMainPage.class);
                    startActivity(intent);
                    break;
                case R.id.questions_icon:
                    Intent intent2= new Intent(GeneralAddictionShowVideos.this,GeneralAddictionShowVideos.class);
                    startActivity(intent2);
                    break;
                case R.id.videos_icon:
                    Intent intent3= new Intent(GeneralAddictionShowVideos.this,GeneralAddictionVideosPage.class);
                    startActivity(intent3);
                    break;
                case R.id.help_icon:
                    //pass
                    break;
                default:
                    System.out.println("Any nav selected");
                    break;
            }
        }

    }
    public void drawerInitialization(){
        navigationView=(NavigationView) findViewById(R.id.nav_view);
        navigationView.setVisibility(View.INVISIBLE);


        navIcon=(ImageView) findViewById(R.id.navigation_icon);
        navIcon.setOnClickListener( new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(navigationView.getVisibility()==View.INVISIBLE){
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
                        break;
                    case R.id.neyi_amacliyoruz_option:
                        break;
                    case R.id.ayarlar_option:
                        break;
                    case R.id.cikis_yap_option:
                        logout();
                        break;
                    default:
                        return true;


                }
                return  false;

            }
        });
    }

    public void logout(){
        auth.signOut();
        Intent intent= new Intent(GeneralAddictionShowVideos.this, LoginPage.class);
        startActivity(intent);
        finish();
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

}