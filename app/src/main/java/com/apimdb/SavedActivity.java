package com.apimdb;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import com.apimdb.domain.Movie;
import com.apimdb.fragments.MovieFragmentSaved;
import com.apimdb.persistencia.Controller;
import com.apimdb.persistencia.CreateDataBase;

import java.util.ArrayList;

import static com.apimdb.R.mipmap.ic_filmreel_black;

public class SavedActivity extends AppCompatActivity {
    private Toolbar toolbar;
    public ArrayList<Movie> myList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);
        toolbar = (Toolbar) findViewById(R.id.my_toolbar_saved);
        toolbar.setLogo(R.drawable.therealogo);
        toolbar.setTitle("My Movies");
        setSupportActionBar(toolbar);

        Controller crud = new Controller(getBaseContext());
        String campos[] = {CreateDataBase.tabela.TITLE, CreateDataBase.tabela.PLOT, CreateDataBase.tabela.YEAR, CreateDataBase.tabela.DIRECTOR, CreateDataBase.tabela.ACTORS, CreateDataBase.tabela.GENRE, CreateDataBase.tabela.RUNTIME, CreateDataBase.tabela.RATED, CreateDataBase.tabela.RELEASED, CreateDataBase.tabela.IMDBID, CreateDataBase.tabela.IMDBRATING, CreateDataBase.tabela.LANGUAGE, CreateDataBase.tabela.IMAGE};

        Cursor cursor = crud.CarregaDados(CreateDataBase.NOME_TABELA, campos);

        myList = new ArrayList<Movie>();

        for (int i = 0; i < cursor.getCount(); i++) {

            String title = cursor.getString(0);
            String plot = cursor.getString(1);
            String year = cursor.getString(2);
            String director = cursor.getString(3);
            String actors = cursor.getString(4);
            String genre = cursor.getString(5);
            String runtime = cursor.getString(6);
            String rated = cursor.getString(7);
            String released = cursor.getString(8);
            String imdbid = cursor.getString(9);
            String imdbrating = cursor.getString(10);
            String language = cursor.getString(11);
            Bitmap imagem = blobtobitmap(cursor.getBlob(12));


            myList.add(new Movie(title,plot,year,director,actors,genre,runtime,rated,released,imdbid,imdbrating,language,imagem));
            cursor.moveToNext();
        }

        MovieFragmentSaved fra = (MovieFragmentSaved) getSupportFragmentManager().findFragmentByTag("mainFragSaved");

            fra = new MovieFragmentSaved();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.myIncFragmentContainer_saved, fra, "mainFragSaved");
            ft.commit();

    }
    public void refresh(){          //refresh is onClick name given to the button
        onRestart();
    }

    public Bitmap blobtobitmap(byte[] blob) {
        if(blob != null) {
            return BitmapFactory.decodeByteArray(blob, 0, blob.length);
        }

        return null;
    }
    @Override
    public void onRestart() {

        super.onRestart();
        Intent refresh = new Intent(this, SavedActivity.class);
        startActivity(refresh);//Start the same Activity
        finish(); //finish Activity.


    }

}
