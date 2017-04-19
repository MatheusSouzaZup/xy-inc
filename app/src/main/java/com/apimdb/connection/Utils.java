package com.apimdb.connection;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import com.apimdb.domain.Movie;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


public class Utils {
    private static Utils instance;
    private List<Movie> myList;

    public static Utils getInstance() {
        if (instance == null) {
            instance = new Utils();
        }

        return instance;
    }

    public List<Movie> getImages(List<Movie> movies) {

        for (Movie f : movies) {
            f.setImagem(downloadImage(f.getPoster()));
        }
        myList = movies;
        return movies;
    }

    public Bitmap downloadImage(String urlname) {
        Bitmap image = null;
        try {
            URL url = new URL(urlname);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = connection.getInputStream();
            image = BitmapFactory.decodeStream(inputStream);
            inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public String getUrlAllInformation(String imdbid) {
        return "http://www.omdbapi.com/?i=" + imdbid;
    }

    public boolean checkConnection(Context context) {
        boolean conected;
        ConnectivityManager conectivityManager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        if (conectivityManager.getActiveNetworkInfo() != null
                && conectivityManager.getActiveNetworkInfo().isAvailable()
                && conectivityManager.getActiveNetworkInfo().isConnected()) {
            conected = true;
        } else {
            conected = false;
        }
        return conected;
    }

    public List<Movie> getmyList() {
        return myList;
    }

    public Bitmap convert(Context context, int imageid) {
        return BitmapFactory.decodeResource(context.getResources(), imageid);
    }
}
