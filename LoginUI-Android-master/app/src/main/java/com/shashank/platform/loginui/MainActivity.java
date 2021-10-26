package com.shashank.platform.loginui;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity {
    implements void OnMapReadyCallback {
        GoogleMap mMap;

        @Override
                protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById((R.id.map));
            mapFragment.getMapAsync(this);
        }

        @Override
                public void onMapReady(final GoogleMap googleMap) {
            mMap = googleMap;

            LatLng SEOUL = new LatLng(37.56, 126.97);

            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(SEOUL);
            markerOptions.title('서울');
            markerOptions.snippet("한국의 수도");
            mMap.addMarker(markerOptions);

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SEOUL, 10));
        }
    }

    private Button btn_sign_in;
    private Button btn_sign_up;

    ImageView imageView;
    TextView textView;
    int count = 0;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);

        btn_sign_in = findViewById(R.id.btn_sign_in);
        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SubActivity.class);
                startActivity(intent);
            }
        });
        btn_sign_up = findViewById(R.id.btn_sign_up);
        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });


        imageView.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext()) {
            public void onSwipeTop() {
            }

            public void onSwipeRight() {

            }

            public void onSwipeLeft() {

            }

            public void onSwipeBottom() {
            }

        });
    }
}
