package com.apimdb;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.apimdb.connection.Utils;
import com.apimdb.domain.Movie;
import com.apimdb.fragments.DefaultFragment;
import com.apimdb.fragments.MovieFragment;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;
import static com.apimdb.R.mipmap.ic_filmreel_black;

public class MainActivity extends AppCompatActivity{
    private Toolbar myToolbar;
    private List<Movie> list = new ArrayList<Movie>();
    private List<Movie> oscarList = new ArrayList<Movie>();
    private String search;
    private String url = "http://www.omdbapi.com/?s=";
    private Movie filmeobj = new Movie();
    private AlertDialog.Builder dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setLogo(R.drawable.therealogo);
        setSupportActionBar(myToolbar);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        filmeobj.oscarMovies(getApplicationContext());
        oscarList = filmeobj.getOscarList();
        fillactivity();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_principal, menu);
        SearchView sv = (SearchView) menu.findItem(R.id.menuSearch).getActionView();
        sv.setOnQueryTextListener(new SearchFiltro());
        return super.onCreateOptionsMenu(menu);
    }
    private class SearchFiltro implements SearchView.OnQueryTextListener {

        @Override
        public boolean onQueryTextChange(String newText) {

            return false;
        }

        @Override
        public boolean onQueryTextSubmit(String query) {
            search = query.toString().replace(' ', '+');
            mySearch();
            return false;
        }
    }
    public String geturl(){
        return url +search;
    }
    public void mySearch() {
        final ProgressDialog load;
        final Utils util = new Utils();
        load = ProgressDialog.show(MainActivity.this,"","Loading...",true);
        if(util.checkConnection(MainActivity.this) == true) {
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, geturl(), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    List<Movie> movies = null;
                    boolean checkerror = false;
                    try {
                        checkerror = Boolean.parseBoolean(response.getString("Response"));
                    }
                    catch (JSONException e){
                        e.printStackTrace();
                    }

                    if(checkerror) {
                        try {
                            movies = Arrays.asList(new Gson().fromJson(response.getJSONArray("Search").toString(), Movie[].class));
                            list = util.getImages(movies);
                            callMovieFragment();
                            load.dismiss();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            load.dismiss();
                        }
                    }
                    else {
                        load.dismiss();
                        callerrordialog();
                    }
                }

            }, new ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("Erro");
                    error.printStackTrace();
                }
            }
            );

            MyApplication.getInstance().addToRequestQueue(request);
        }
        else{
            Toast.makeText(MainActivity.this, "Sem Conexão", Toast.LENGTH_SHORT).show();
        }

        }
    public void callMovieFragment() {
            MovieFragment fra = (MovieFragment) getSupportFragmentManager().findFragmentByTag("mainFrag");
            fra = new MovieFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.myIncFragmentContainer, fra, "mainFrag");
            ft.commit();
        }
    public void callDefaultFragment() {
        DefaultFragment defaultFragment = (DefaultFragment) getSupportFragmentManager().findFragmentByTag("mainFragDefault");
        defaultFragment = new DefaultFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.myIncFragmentContainer, defaultFragment, "mainFragDefault");
        ft.commit();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.menuSavedMovies:
                    Intent intent = new Intent(MainActivity.this, SavedActivity.class);
                    startActivity(intent);
                    return true;

                default:
                    return super.onOptionsItemSelected(menuItem);
            }
        }
    public List<Movie> getList(){
        return list;
    }
    public List<Movie> getmyOscarList() {
        return oscarList;
    }
    public void fillactivity() {
            callDefaultFragment();
    }
    public void callerrordialog(){
        dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle("Erro na pesquisa");
        dialog.setMessage("Insira uma pesquisa valida!");
        dialog.create();
        dialog.show();
    }
}
