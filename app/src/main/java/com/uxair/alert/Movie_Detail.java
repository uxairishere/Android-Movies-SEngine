package com.uxair.alert;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class Movie_Detail extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        TextView textViewTitle;
        ImageView imageViewPoster;
        TextView textViewYear;
        TextView textViewType;
        TextView textViewDesc;
        TextView textViewRating;
        ImageView imageViewBACKGROUND;
        SwipeRefreshLayout swipeRefreshLayout;

        swipeRefreshLayout = findViewById(R.id.swiperefresh);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                finish();
                startActivity(getIntent());
            }
        });

        textViewTitle = findViewById(R.id.textViewMovieTitle);
        textViewYear = findViewById(R.id.textViewMovieYear);
        textViewType = findViewById(R.id.textViewMovieType);
        textViewDesc = findViewById(R.id.textViewMovieDesc);
        imageViewPoster = findViewById(R.id.imageViewMoviePoster);
        textViewRating = findViewById(R.id.textViewMovieRating);
        imageViewBACKGROUND = findViewById(R.id.imageViewBACKGROUND);

        Bundle extras = getIntent().getExtras();
        textViewTitle.setText(extras.getString("TITLE"));
        Glide.with(getApplicationContext()).load(extras.getString("BACKDROP")).into(imageViewPoster);
        Glide.with(getApplicationContext()).load(extras.getString("POSTER")).into(imageViewBACKGROUND);
        textViewDesc.setText(extras.getString("DESC"));
        textViewYear.setText(extras.getString("YEAR"));
        textViewType.setText(extras.getString("TYPE"));
        textViewRating.setText(extras.getString("RATING"));



    }
}