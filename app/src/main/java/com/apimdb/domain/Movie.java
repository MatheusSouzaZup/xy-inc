package com.apimdb.domain;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.apimdb.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matheus on 19/03/2017.
 */

public class Movie {
    List<Movie> oscarList = new ArrayList<>();
    private String Title;
    private String Plot;
    private String Year;
    private String Rated;
    private String Released;
    private String Runtime;
    private String Genre;
    private String Director;
    private String Actors;
    private String Language;
    private String imdbID;
    private String imdbRating;
    private String Writer;
    private String Poster;
    private Bitmap imagem;

    public Movie(String title , String plot, String year, String director, String actors, String genre, String runtime, String rated, String released, String imdbid, String imdbrating, String language, Bitmap imagem) {
        this.Title = title;
        this.Plot = plot;
        this.Year = year;
        this.Director = director;
        this.Actors = actors;
        this.Genre = genre;
        this.Runtime = runtime;
        this.Rated = rated;
        this.Released = released;
        this.imdbID = imdbid;
        this.imdbRating = imdbrating;
        this.Language = language;
        this.imagem = imagem;
    }
    public Movie(){}

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        this.Title = title;
    }

    public String getPlot() {
        return Plot;
    }

    public void setPlot(String plot) {
        this.Plot = plot;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public String getRated() {
        return Rated;
    }

    public void setRated(String rated) {
        Rated = rated;
    }

    public String getReleased() {
        return Released;
    }

    public void setReleased(String released) {
        Released = released;
    }

    public String getRuntime() {
        return Runtime;
    }

    public void setRuntime(String runtime) {
        Runtime = runtime;
    }

    public String getGenre() {
        return Genre;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }

    public String getDirector() {
        return Director;
    }

    public void setDirector(String director) {
        Director = director;
    }

    public String getActors() {
        return Actors;
    }

    public void setActors(String actors) {
        Actors = actors;
    }

    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String language) {
        Language = language;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;
    }

    public String getWriter() {
        return Writer;
    }

    public void setWriter(String writer) {
        Writer = writer;
    }

    public String getPoster() {
        return Poster;
    }

    public void setPoster(String poster) {
        Poster = poster;
    }

    public Bitmap getImagem() {
        return imagem;
    }

    public void setImagem(Bitmap imagem) {
        this.imagem = imagem;
    }

    @Override
    public String toString() {
        return "Sinopse: " + getPlot()+ "\n"+
                "Duração: " + getRuntime() + "\n"+
                "Atores: " + getActors() + "\n"+
                "Diretores: "+ getDirector() +"\n"+
                "Genero: "+ getGenre() + "\n"+
                "Data Lançamento: "+ getReleased() + "\n"+
                "Linguas: "+ getLanguage() + "\n"+
                "Nota IMDB: "+ getImdbRating() + "\n";

    }
    public List<Movie> oscarMovies(Context context){
        Movie oscarmovie;

        oscarmovie = new Movie();
        oscarmovie.setTitle("Moonlight"); oscarmovie.setImagem(convert(context, R.drawable.moonlight)); oscarmovie.setImdbID("tt4975722");
        oscarList.add(oscarmovie);

        oscarmovie = new Movie();
        oscarmovie.setTitle("Arrival"); oscarmovie.setImagem(convert(context, R.drawable.arrival));oscarmovie.setImdbID("tt2543164");
        oscarList.add(oscarmovie);

        oscarmovie = new Movie();
        oscarmovie.setTitle("Fences"); oscarmovie.setImagem(convert(context, R.drawable.fences));oscarmovie.setImdbID("tt2671706");
        oscarList.add(oscarmovie);

        oscarmovie = new Movie();
        oscarmovie.setTitle("Hacksaw Ridge"); oscarmovie.setImagem(convert(context, R.drawable.hacksawridge));oscarmovie.setImdbID("tt2119532");
        oscarList.add(oscarmovie);

        oscarmovie = new Movie();
        oscarmovie.setTitle("La La Land"); oscarmovie.setImagem(convert(context, R.drawable.lalaland));oscarmovie.setImdbID("tt3783958");
        oscarList.add(oscarmovie);

        oscarmovie = new Movie();
        oscarmovie.setTitle("Manchester By The Sea"); oscarmovie.setImagem(convert(context, R.drawable.manchester));oscarmovie.setImdbID("tt4034228");
        oscarList.add(oscarmovie);

        oscarmovie = new Movie();
        oscarmovie.setTitle("Suicide Squad"); oscarmovie.setImagem(convert(context, R.drawable.suicidesquad));oscarmovie.setImdbID("tt1386697");
        oscarList.add(oscarmovie);

        oscarmovie = new Movie();
        oscarmovie.setTitle("The Jungle Book"); oscarmovie.setImagem(convert(context, R.drawable.thejunglebook));oscarmovie.setImdbID("tt3040964");
        oscarList.add(oscarmovie);

        oscarmovie = new Movie();
        oscarmovie.setTitle("Zootopia"); oscarmovie.setImagem(convert(context, R.drawable.zootopia));oscarmovie.setImdbID("tt2948356");
        oscarList.add(oscarmovie);

        oscarmovie = new Movie();
        oscarmovie.setTitle("OJ: Made in America"); oscarmovie.setImagem(convert(context, R.drawable.ojmadeinamerica));oscarmovie.setImdbID("tt5275892");
        oscarList.add(oscarmovie);

        oscarmovie = new Movie();
        oscarmovie.setTitle("The Salesman"); oscarmovie.setImagem(convert(context, R.drawable.thesalesman));oscarmovie.setImdbID("tt5186714");
        oscarList.add(oscarmovie);

        oscarmovie = new Movie();
        oscarmovie.setTitle("The White Helmets"); oscarmovie.setImagem(convert(context, R.drawable.whitehelmets));oscarmovie.setImdbID("tt6073176");
        oscarList.add(oscarmovie);
        return oscarList;
    }
    public List<Movie> getOscarList() {
        return oscarList;
    }

    public Bitmap convert(Context context, int imageid){
        return BitmapFactory.decodeResource(context.getResources(), imageid);
    }

}
