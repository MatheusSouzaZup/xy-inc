package com.apimdb;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.apimdb.adapter.MovieAdapter;
import com.apimdb.connection.Utils;
import com.apimdb.domain.Movie;
import com.apimdb.persistencia.Controller;
import com.apimdb.persistencia.CreateDataBase;
import com.google.gson.Gson;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;


public class ExtendActivity extends AppCompatActivity {
    private TextView tvTitle;
    private TextView tvInfos;
    private ImageView ivImage;
    private Toolbar myToolbar;
    private ImageButton imageButton;
    private Movie myMovie;
    String imdbid, title;
    Bitmap image;
    int position;
    int contextint = 0;
    private Utils utils = new Utils();
    private Controller controllerDB;
    private String urlimage;
    private NetworkImageView networkImageView;
    private RequestQueue mRequestQueue;
    private ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extend);
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar_extend);
        controllerDB = new Controller(getApplicationContext());
        initCompontents();

    }
    private void initCompontents() {

        Intent intent = getIntent();

        byte[] imageByteArray = null;

        if (intent != null) {
            Bundle params = intent.getExtras();

            if (params != null) {
                imdbid = params.getString(MovieAdapter.KEY_IMDBID);
                contextint = params.getInt("contextint");
                imageByteArray = params.getByteArray("image");
                position = params.getInt("pos");
                title = params.getString("title");
                urlimage = params.getString("urlimage");
            }
        }
        image = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);
        myToolbar.setTitle(title);
        setSupportActionBar(myToolbar);
        networkImageView = (NetworkImageView) findViewById(R.id.extend_my_image);
        tvTitle = (TextView) findViewById(R.id.extend_my_title);
        tvInfos = (TextView) findViewById(R.id.extent_my_plot);
        imageButton = (ImageButton) findViewById(R.id.imageButton);
        if(contextint == 2){
            View v = findViewById(R.id.imageButton);
            imageButton.setVisibility(View.GONE);
            getinfosfrombd();
        }
        else{
            getInfosFromService();
        }
    }
    private void getinfosfrombd() {
        Controller crud = new Controller(getBaseContext());
        String campos[] = {CreateDataBase.tabela.TITLE, CreateDataBase.tabela.PLOT, CreateDataBase.tabela.YEAR, CreateDataBase.tabela.DIRECTOR, CreateDataBase.tabela.ACTORS, CreateDataBase.tabela.GENRE, CreateDataBase.tabela.RUNTIME, CreateDataBase.tabela.RATED, CreateDataBase.tabela.RELEASED, CreateDataBase.tabela.IMDBID, CreateDataBase.tabela.IMDBRATING, CreateDataBase.tabela.LANGUAGE, CreateDataBase.tabela.IMAGE};

        Cursor cursor = crud.searchMovie(CreateDataBase.NOME_TABELA, imdbid);

        myMovie = null;

        for (int i = 0; i < cursor.getCount(); i++) {

            String title = cursor.getString(0);
            String year = cursor.getString(1);
            String rated = cursor.getString(2);
            String released = cursor.getString(3);
            String runtime = cursor.getString(4);
            String genre = cursor.getString(5);
            String director = cursor.getString(6);
            String actors = cursor.getString(7);
            String plot = cursor.getString(8);
            String language = cursor.getString(9);
            String imdbid = cursor.getString(10);
            String imdbrating = cursor.getString(11);
            Bitmap imagem = blobtobitmap(cursor.getBlob(12));
            urlimage = cursor.getString(cursor.getColumnIndex("poster"));

            myMovie = new Movie(title,plot,year,director,actors,genre,runtime,rated,released,imdbid,imdbrating,language,imagem);
            myMovie.setPoster(urlimage);
            cursor.moveToNext();
        }
        fillActivitybyBD(myMovie);

    }
    public Bitmap blobtobitmap(byte[] blob) {
        if(blob != null) {
            return BitmapFactory.decodeByteArray(blob, 0, blob.length);
        }

        return null;
    }
    private void fillActivitybyService(Movie f) {
        myMovie = f;
        mRequestQueue = MyApplication.getInstance().getRequestQueue();
        imageLoader = MyApplication.getInstance().getImageLoader();
        imageLoader.get(myMovie.getPoster(),ImageLoader.getImageListener(networkImageView,250,250));
        networkImageView.setImageUrl(myMovie.getPoster(),imageLoader);
        networkImageView.setDefaultImageResId(R.drawable.notfound);
        networkImageView.setErrorImageResId(R.drawable.notfound);
        tvTitle.setText(title);
        tvInfos.setText(myMovie.toString());

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = setupvalues(myMovie);
                long resultado = controllerDB.inserirDados(CreateDataBase.NOME_TABELA, values);
                 if (resultado == -1) {
                     Toast.makeText(getApplicationContext(), "Este item já esta salvo!", Toast.LENGTH_SHORT).show();
                } else {
                     Toast.makeText(getApplicationContext(), "Salvo!", Toast.LENGTH_SHORT).show();
                 }
            }
        });
    }
    private void fillActivitybyBD(Movie f) {
        myMovie = f;
        mRequestQueue = MyApplication.getInstance().getRequestQueue();
        imageLoader = MyApplication.getInstance().getImageLoader();
        imageLoader.get(myMovie.getPoster(),ImageLoader.getImageListener(networkImageView,250,250));
        networkImageView.setImageUrl(myMovie.getPoster(),imageLoader);
        networkImageView.setDefaultImageResId(R.drawable.notfound);
        networkImageView.setErrorImageResId(R.drawable.notfound);
        tvTitle.setText(title);
        tvInfos.setText(myMovie.toString());

    }
    public byte[] bitmaptoblob(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();

    }
    private ContentValues setupvalues(Movie myMovie) {
        ContentValues values = new ContentValues();
        values.put("TITLE", myMovie.getTitle());
        values.put("YEAR", myMovie.getYear());
        values.put("RATED", myMovie.getRated());
        values.put("RELEASED", myMovie.getReleased());
        values.put("RUNTIME", myMovie.getRuntime());
        values.put("GENRE", myMovie.getGenre());
        values.put("DIRECTOR", myMovie.getDirector());
        values.put("ACTORS", myMovie.getActors());
        values.put("PLOT", myMovie.getPlot());
        values.put("LANGUAGE", myMovie.getLanguage());
        values.put("IMAGE", bitmaptoblob(myMovie.getImagem()));
        values.put("IMDBID", myMovie.getImdbID());
        values.put("IMDBRATING", myMovie.getImdbRating());
        values.put("POSTER", myMovie.getPoster());
        return values;
    }
    public void getInfosFromService() {
        if (utils.checkConnection(ExtendActivity.this) == true) {
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, utils.getUrlAllInformation(imdbid), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Movie movie;
                    movie = new Gson().fromJson(response.toString(), Movie.class);
                    myMovie = movie;
                    myMovie.setImagem(image);
                    fillActivitybyService(myMovie);
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println(getString(R.string.error));
                    error.printStackTrace();
                }
            }
            );

            MyApplication.getInstance().addToRequestQueue(request);
        } else {
            Toast.makeText(ExtendActivity.this, getString(R.string.not_connect), Toast.LENGTH_SHORT).show();
        }
    }

}