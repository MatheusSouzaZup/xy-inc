package com.apimdb.persistencia;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ZUP on 22/03/2017.
 */

public class CreateDataBase extends SQLiteOpenHelper{

    private static final String NOME = "bancoApi.db";
    public static final String NOME_TABELA = "salvos";
    public static DataBase tabela = new DataBase();
    private static final int VERSION = 3;
    private static CreateDataBase instance;

    public CreateDataBase(Context context)
    {
        super(context,NOME,null,VERSION);

    }


    public static synchronized CreateDataBase getInstance(Context context){
        if(instance == null)
            instance = new CreateDataBase(context);
        return instance;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + NOME_TABELA + "(" + tabela.campos() + ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + NOME_TABELA);
        this.onCreate(db);
    }
}
