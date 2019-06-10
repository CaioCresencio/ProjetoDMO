package com.ifsp.droidteca.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ifsp.droidteca.R;
import com.ifsp.droidteca.model.Biblioteca;
import com.ifsp.droidteca.model.Livro;

public class ListarLivros extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livros);
        Biblioteca biblioteca = Biblioteca.getInstance();
        ListView listView = findViewById(R.id.listaaaaa);

        listView.setAdapter(new ArrayAdapter<Livro>(this,android.R.layout.simple_list_item_1,biblioteca.getLivros()));
    }

}
