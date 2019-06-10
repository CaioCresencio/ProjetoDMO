package com.ifsp.droidteca.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;

import com.ifsp.droidteca.model.Biblioteca;
import com.ifsp.droidteca.model.Livro;
import com.ifsp.droidteca.model.Pessoa;

import java.math.BigInteger;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class BancoBibliotecaDAO {

    private static final String BANCO_BIBLIOTECA = "bd_biblioteca";

    private SQLiteDatabase banco;
    private BancoBibliotecaHelper bancoOpenHelper;

    public BancoBibliotecaDAO(Context context){
        bancoOpenHelper = new BancoBibliotecaHelper(context, BANCO_BIBLIOTECA, null, 1);
    }

    public void abrir(){
        banco = bancoOpenHelper.getWritableDatabase();
    }

    public void fechar(){
        if(banco != null){
            banco.close();
        }
    }

    public void salvarEstado(){
        Biblioteca biblioteca = Biblioteca.getInstance();
        abrir();
        deletar();
        addPessoas(biblioteca.getPessoas());
        addLivros(biblioteca.getLivros());
        fechar();
    }

    public void addPessoas(ArrayList<Pessoa> pessoas){
        ContentValues values = new ContentValues();

        for(Pessoa p: pessoas) {
            values.put("nome_pessoa", p.getNome());
            values.put("qtdExemplar", p.getQtdEmp());
            banco.insert(bancoOpenHelper.TABELA_PESSOA, null, values);
        }

    }

    public void addLivros(ArrayList<Livro> livros){
        ContentValues values = new ContentValues();

        for(Livro l: livros) {
            values.put("titulo", l.getTitulo());
            values.put("autor", l.getAutor());
            if(l.getPessoa() == null){
                values.put("nome_pessoa", "null");
            }else{
                values.put("nome_pessoa", l.getPessoa().getNome());
            }
            banco.insert(bancoOpenHelper.TABELA_LIVRO, null, values);
        }

    }

    public void deletar(){
        banco.delete(bancoOpenHelper.TABELA_PESSOA, null, null);
        banco.delete(bancoOpenHelper.TABELA_LIVRO, null, null);
    }

    public void deletePessoa(Pessoa p){
        banco.delete(bancoOpenHelper.TABELA_PESSOA, "id_pessoa = ?", new String[]{p.getNome()});
    }

    public void deleteLivro(Livro l){
        banco.delete(bancoOpenHelper.TABELA_LIVRO, "id_livro = ?", new String[] {String.valueOf(l.getId())});
    }

    public ArrayList<Livro> getLivros(){
        banco = bancoOpenHelper.getReadableDatabase();
        Cursor cursor =  banco.query(bancoOpenHelper.TABELA_LIVRO, new String[] {"id_livro", "titulo", "autor","nome_pessoa"}, null, null, null, null, null);
        ArrayList<Livro> livros = new ArrayList<>();
        try {
            if (cursor.getColumnCount() >= 0) {
                cursor.moveToNext();
                do {
                    Pessoa p = null;
                    if (!cursor.getString(3).equals("null")) {
                        p = getPessoaPeloNome(cursor.getString(3));
                    }

                    livros.add(new Livro(cursor.getInt(0), cursor.getString(1), cursor.getString(2), p));

                } while (cursor.moveToNext());

            }
        }catch(CursorIndexOutOfBoundsException e){
            livros = new ArrayList<Livro>();
        }

        return livros;
    }

    public ArrayList<Pessoa> getPessoas() {
        banco = bancoOpenHelper.getReadableDatabase();
        Cursor cursor =  banco.query(bancoOpenHelper.TABELA_PESSOA, new String[] {"nome_pessoa", "qtdExemplar"}, null, null, null, null, null);
        ArrayList<Pessoa> pessoas = new ArrayList<>();
        try {
            if (cursor.getColumnCount() >= 0) {
                cursor.moveToNext();
                do {
                    pessoas.add(new Pessoa(cursor.getString(0), cursor.getInt(1)));
                } while (cursor.moveToNext());

            }
        }catch (CursorIndexOutOfBoundsException e){
            pessoas = new ArrayList<>();
        }

        return pessoas;
    }

    public Pessoa getPessoaPeloNome(String nome){
        Cursor cursor =  banco.query(bancoOpenHelper.TABELA_PESSOA, new String[] {"nome_pessoa", "qtdExemplar"}, "nome_pessoa = '"+nome+"'", null, null, null, null);
        Pessoa p = null;

        if(cursor.getColumnCount()>= 0){
            cursor.moveToNext();
            do{
                if(!nome.equals("null")) {
                    p = new Pessoa(cursor.getString(0), cursor.getInt(1));
                }
            }while (cursor.moveToNext());

        }

        return p;
    }

}
