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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;

public class GeneralAddictionShowVideos extends AppCompatActivity {

    Toolbar tb, bottom;
    NavigationView navigationView;

    FirebaseAuth auth;
    RelativeLayout relativeLayout;

    ImageView navIcon,backIcon;
    TextView homeIcon,questionIcon,videoIcon, helpIcon, titleView, descriptionView;
    YouTubePlayerView youTubePlayerView;
    FrameLayout fullscreenViewContainer;


    GridView recommendedGrid;
    YouTubePlayer youTubePlayer;

    String title, videoId;

    String [] videoTitles, videoIds,descriptions;

    int skippingPos;
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
        setRecommendedVideos();
        backToVideoList();
    }
    public void toolBarArrangement(){
        tb=(Toolbar) findViewById(R.id.toolbar);
        tb.setTitle("Bağımlılık");
    }

    public void getExtrasFromIntent(){
        Bundle extras= getIntent().getExtras();

        skippingPos=extras.getInt("VIDEO_POS");
        videoTitles=extras.getStringArray("VIDEO_LIST");
        videoIds=extras.getStringArray("VIDEO_IDS");
        descriptions=extras.getStringArray("VIDEO_DESCRIPTIONS");

        titleView=(TextView) findViewById(R.id.video_title_view);
        titleView.setText(videoTitles[skippingPos]);

        descriptionView=(TextView) findViewById(R.id.short_description);
        descriptionView.setText(descriptions[skippingPos]);

    }


    public void setRecommendedVideos(){


        recommendedGrid=(GridView)findViewById(R.id.recommended_grid);

        List<Integer> secilenIndeksler = new ArrayList<>();
        List<String> secilenBasliklar= new ArrayList<>();
        Random random = new Random();

        while (secilenIndeksler.size() < 3) {
            int rastgeleIndex = random.nextInt(videoIds.length);
            if (rastgeleIndex != skippingPos && !secilenIndeksler.contains(rastgeleIndex)) {
                secilenIndeksler.add(rastgeleIndex);
                secilenBasliklar.add(videoTitles[rastgeleIndex]);
            }
        }

        ArrayAdapter adapter= new ArrayAdapter<>(GeneralAddictionShowVideos.this,R.layout.general_addiction_recommended_videos_grid,secilenBasliklar);
        recommendedGrid.setAdapter(adapter);
        recommendedGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){

                    case 0:
                        //System.out.println("1");
                        Intent intent = new Intent( GeneralAddictionShowVideos.this,GeneralAddictionShowVideos.class);
                        Bundle extras = new Bundle();
                        extras.putInt("VIDEO_POS", secilenIndeksler.get(0));
                        extras.putStringArray("VIDEO_LIST",videoTitles);
                        extras.putStringArray("VIDEO_IDS",videoIds);
                        extras.putStringArray("VIDEO_DESCRIPTIONS",descriptions);
                        intent.putExtras(extras);
                        startActivity(intent);
                        finish();
                        break;
                    case 1:
                        //System.out.println("2");
                        Intent intent2 = new Intent( GeneralAddictionShowVideos.this,GeneralAddictionShowVideos.class);
                        Bundle extras2 = new Bundle();
                        extras2.putInt("VIDEO_POS", secilenIndeksler.get(1));
                        extras2.putStringArray("VIDEO_LIST",videoTitles);
                        extras2.putStringArray("VIDEO_IDS",videoIds);
                        extras2.putStringArray("VIDEO_DESCRIPTIONS",descriptions);
                        intent2.putExtras(extras2);
                        startActivity(intent2);
                        finish();
                        break;
                    case 2:
                        //System.out.println("3");
                        Intent intent3 = new Intent( GeneralAddictionShowVideos.this,GeneralAddictionShowVideos.class);
                        Bundle extras3 = new Bundle();
                        extras3.putInt("VIDEO_POS", secilenIndeksler.get(2));
                        extras3.putStringArray("VIDEO_LIST",videoTitles);
                        extras3.putStringArray("VIDEO_IDS",videoIds);
                        extras3.putStringArray("VIDEO_DESCRIPTIONS",descriptions);
                        intent3.putExtras(extras3);
                        startActivity(intent3);
                        finish();
                        break;
                    default:
                        break;
                }
            }
        });

        // Seçilen indeksleri yazdırma
        System.out.println("Seçilen indeksler:");
        for (int indeks : secilenIndeksler) {
            System.out.println(indeks);
        }

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
                youTubePlayer.cueVideo(videoIds[skippingPos], 0f);


            }
        }, iFramePlayerOptions);
        youTubePlayerView.addFullscreenListener(new FullscreenListener() {

            @Override
            public void onEnterFullscreen(@NonNull View fullscreenview, @NonNull Function0<Unit> exitFullscreen) {
                isFullscreen[0] = true;

                // the video will continue playing in fullscreenView


                fullscreenViewContainer.setVisibility(View.VISIBLE);

                toggleInvisible();
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

                toggleVisible();

            }




        });


        getLifecycle().addObserver(youTubePlayerView);



    }
    public void  toggleInvisible(){
        youTubePlayerView.setVisibility(View.GONE);
        tb.setVisibility(View.GONE);
        navigationView.setVisibility(View.GONE);
        bottom.setVisibility(View.GONE);
        titleView.setVisibility(View.GONE);
        recommendedGrid.setVisibility(View.GONE);
        descriptionView.setVisibility(View.GONE);
        backIcon.setVisibility(View.GONE);
    }

    public void toggleVisible(){
        youTubePlayerView.setVisibility(View.VISIBLE);
        tb.setVisibility(View.VISIBLE);
        navigationView.setVisibility(View.INVISIBLE);
        bottom.setVisibility(View.VISIBLE);
        titleView.setVisibility(View.VISIBLE);
        recommendedGrid.setVisibility(View.VISIBLE);
        descriptionView.setVisibility(View.VISIBLE);
        backIcon.setVisibility(View.VISIBLE);
    }

    public void backToVideoList(){
        backIcon=(ImageView)findViewById(R.id.back_to_video_list_btn);
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(GeneralAddictionShowVideos.this,GeneralAddictionVideosPage.class);
                startActivity(intent);
                finish();
            }
        });
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
                    Intent intent2= new Intent(GeneralAddictionShowVideos.this,GeneralAddictionQuestionsPage.class);
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