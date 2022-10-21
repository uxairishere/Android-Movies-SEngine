package com.uxair.alert;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class BrowseMovies extends AppCompatActivity {

    static String removeSpace(String str)
    {
        str = str.replaceAll("\\s","+");
        return str;
    }

    public class MovieAPICall extends AsyncTask<String, Void, String> {


        private ArrayList<Movies_Data> arrayList;

        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }

                return result;
            } catch (Exception e) {
                e.printStackTrace();
                return "Failed";
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {

                    //TODO your background code
                }
            });

            try {
                JSONObject jsonObject = new JSONObject(s);
                String search = jsonObject.getString("results");
                JSONArray jsonArray = new JSONArray(search);
//                Toast.makeText(getApplicationContext(), "SUCCESS" , Toast.LENGTH_LONG).show();
                arrayList = new ArrayList<Movies_Data>();

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonEachMovie = jsonArray.getJSONObject(i);
                    String title = jsonEachMovie.getString("title");
                    String poster = "https://image.tmdb.org/t/p/original/" + jsonEachMovie.getString("poster_path");

                    String year = jsonEachMovie.getString("release_date");
                    String desc = jsonEachMovie.getString(("overview"));
                    String votes = jsonEachMovie.getString("vote_average");
                    String adult = jsonEachMovie.getString("adult");
                    String backdrop = "https://image.tmdb.org/t/p/original/" + jsonEachMovie.getString("backdrop_path");
                    String type = "movie";
                    arrayList.add(new Movies_Data(title, poster, year, type, desc, votes, backdrop));
                }

                RecyclerView recyclerView = findViewById(R.id.moviesRecyclerView);
                LinearLayoutManager llm = new LinearLayoutManager(BrowseMovies.this);
                recyclerView.setLayoutManager(llm);
                recyclerView.setHasFixedSize(true);
                MoviesAdapter moviesAdapter = new MoviesAdapter(getApplicationContext(), arrayList);
                llm.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setAdapter(moviesAdapter);

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "MOVIE API CALL FAIL", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class SearchMovie implements Runnable{
        String name = "";
        public SearchMovie(String name){
            this.name = name;
        }
        @Override
        public void run() {
            MovieAPICall task1 = new MovieAPICall();
            task1.execute("https://api.themoviedb.org/3/search/movie?api_key=ff998e112c7de0aacac0c7ca8e2815b3&query=" + name);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_movies);

        EditText editTextSearch = findViewById(R.id.editTextTextSearch);
        Button button = findViewById(R.id.buttonSearch);
        MovieAPICall task = new MovieAPICall();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = removeSpace(editTextSearch.getText().toString());

                new Thread(new SearchMovie(name)).start();
            }
        });

        task.execute("https://api.themoviedb.org/3/discover/movie?api_key=ff998e112c7de0aacac0c7ca8e2815b3&primary_release_year=2022sort_by=popularity.desc");
//        popular new api
//        https://api.themoviedb.org/3/discover/movie?api_key=ff998e112c7de0aacac0c7ca8e2815b3&primary_release_year=2022sort_by=popularity.desc


//      https://api.themoviedb.org/3/search/movie?api_key=ff998e112c7de0aacac0c7ca8e2815b3&query=The+Godfather
//        https://api.themoviedb.org/3/search/movie?api_key=ff998e112c7de0aacac0c7ca8e2815b3&query=inception

        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swiperefreshRecyclerView);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                finish();
                startActivity(getIntent());
            }
        });
    }
}