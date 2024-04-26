package com.example.addictionrecovery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.FullscreenListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.sql.SQLOutput;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;

public class GeneralAddictionShowVideos extends AppCompatActivity {

    Toolbar tb, bottom;
    NavigationView navigationView;

    FirebaseAuth auth;
    RelativeLayout relativeLayout;

    ImageView navIcon;
    TextView homeIcon,questionIcon,videoIcon, helpIcon, titleView;
    YouTubePlayerView youTubePlayerView;
    FrameLayout fullscreenViewContainer;


    YouTubePlayer youTubePlayer;

    String title, videoId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_addiction_show_videos);
        relativeLayout=(RelativeLayout)findViewById(R.id.ga_show_videos_layout);
        fullscreenViewContainer =(FrameLayout) findViewById(R.id.full_screen_view_container) ;

        auth=FirebaseAuth.getInstance();





        getExtrasFromIntent();
        videoArrangements();
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
        title= extras.getString("VIDEO_TITLE");
        videoId= extras.getString("VIDEO_ID");

        titleView=(TextView) findViewById(R.id.video_title_view);
        titleView.setText(title);
    }

    public void videoArrangements(){
        final boolean[] isFullscreen = {false};
        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtube_video_player);
        fullscreenViewContainer =(FrameLayout) findViewById(R.id.full_screen_view_container) ;

        IFramePlayerOptions iFramePlayerOptions= new IFramePlayerOptions.Builder().controls(1).fullscreen(1).build();
        youTubePlayerView.setEnableAutomaticInitialization(false);
        youTubePlayerView.initialize(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                GeneralAddictionShowVideos.this.youTubePlayer = youTubePlayer;
                youTubePlayer.cueVideo(videoId, 0f);


            }
        }, iFramePlayerOptions);
        youTubePlayerView.addFullscreenListener(new FullscreenListener() {

            @Override
            public void onEnterFullscreen(@NonNull View fullscreenview, @NonNull Function0<Unit> exitFullscreen) {
                isFullscreen[0] = true;

                // the video will continue playing in fullscreenView
                youTubePlayerView.setVisibility(View.GONE);
                tb.setVisibility(View.GONE);
                navigationView.setVisibility(View.GONE);
                bottom.setVisibility(View.GONE);
                titleView.setVisibility(View.GONE);




                fullscreenViewContainer.setVisibility(View.VISIBLE);


                fullscreenViewContainer.addView(fullscreenview);
                //fullscreenViewContainer.setRotation(90);
                ViewUtils.rotateAndScaleView(fullscreenViewContainer,GeneralAddictionShowVideos.this);




            }
            @Override
            public void onExitFullscreen() {
                isFullscreen[0] = false;

                System.out.println("ciktim");
                // the video will continue playing in the player
                youTubePlayerView.setVisibility(View.VISIBLE);
                fullscreenViewContainer.setVisibility(View.GONE);
                fullscreenViewContainer.removeAllViews();
                //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                youTubePlayerView.setVisibility(View.VISIBLE);
                tb.setVisibility(View.VISIBLE);
                navigationView.setVisibility(View.INVISIBLE);
                bottom.setVisibility(View.VISIBLE);
                titleView.setVisibility(View.VISIBLE);
                navBottomArrangements();

            }




        });


        getLifecycle().addObserver(youTubePlayerView);



    }

    public static  class ViewUtils {

        public static void rotateAndScaleView(View view, Context context) {
            WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics displayMetrics = new DisplayMetrics();
            if (windowManager != null) {
                windowManager.getDefaultDisplay().getMetrics(displayMetrics);
                int screenWidthPixels = displayMetrics.heightPixels;
                int screenHeightPixels = displayMetrics.widthPixels;

                // View'i 90 derece döndür


                // View'in mevcut LayoutParams'larını al
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();

                //view.setRotation(90);
                // View içeriğini döndürmek için genişlik ve yükseklik yer değiştiriyor
                // Bu yüzden genişlik yerine yüksekliği, yüksekliği yerine genişliği alıyoruz
                layoutParams.width =screenWidthPixels ;
                layoutParams.height = screenHeightPixels;

                // LayoutParams'ları güncelle
                view.setLayoutParams(layoutParams);

                System.out.println(screenHeightPixels);
                System.out.println(screenWidthPixels);

                view.requestLayout();

            }
        }
    }

    public void navBottomArrangements(){
        bottom= (Toolbar) findViewById(R.id.bottom_temp);
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