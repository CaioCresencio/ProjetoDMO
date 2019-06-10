package com.ifsp.droidteca.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ifsp.droidteca.R;
import com.ifsp.droidteca.model.Biblioteca;
import com.ifsp.droidteca.model.Livro;

import java.util.ArrayList;

public class ListarEmp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_emp);
        ListView listView = findViewById(R.id.listaaaaa);
        listView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,getLista()));
        //listView.getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

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
