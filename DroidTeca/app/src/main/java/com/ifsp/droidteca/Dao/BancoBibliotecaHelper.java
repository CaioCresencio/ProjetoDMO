package com.ifsp.droidteca.Dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BancoBibliotecaHelper extends SQLiteOpenHelper {

    protected static final String TABELA_PESSOA = "tb_pessoa";
    protected static final String TABELA_LIVRO = "tb_livro";

    public BancoBibliotecaHelper (Context context, String nome, SQLiteDatabase.CursorFactory fabrica, int versao){
        super(context, nome, fabrica, versao);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String query1 = "CREATE TABLE " + TABELA_PESSOA + "( nome_pessoa TEXT PRIMARY KEY, qtdExemplar INTEGER)";
        String query2 = "CREATE TABLE " + TABELA_LIVRO + "( id_livro INTEGER PRIMARY KEY AUTOINCREMENT, titulo TEXT, autor TEXT, nome_pessoa TEXT, " +
                "FOREIGN KEY(nome_pessoa) REFERENCES tb_pessoa(nome_pessoa))";

        db.execSQL(query1);
        db.execSQL(query2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }



}
