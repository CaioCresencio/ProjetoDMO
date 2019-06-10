package com.ifsp.droidteca.view;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ifsp.droidteca.model.Biblioteca;
import com.ifsp.droidteca.model.Livro;

import java.util.ArrayList;

public class ListarEmprestimos  extends android.app.ListActivity {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);


        this.setListAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,getLista()));
        this.getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

    }

    public ArrayList<String> getLista(){
        ArrayList<String> lista = new ArrayList<>();
        ArrayList<Livro> livros = Biblioteca.getInstance().getLivrosEmprestados();
        for(Livro l: livros){
            StringBuilder sb = new StringBuilder();
            sb.append("Livro: ");
            sb.append(l.getTitulo());
            sb.append("\n");
            sb.append("Pessoa: ");
            sb.append(l.getPessoa().getNome());
            lista.add(String.format(sb.toString()));
        }
        return lista;
    }
}
