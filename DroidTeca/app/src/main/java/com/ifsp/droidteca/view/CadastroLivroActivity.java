package com.ifsp.droidteca.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ifsp.droidteca.R;
import com.ifsp.droidteca.model.Biblioteca;
import com.ifsp.droidteca.model.Livro;
import com.ifsp.droidteca.model.Pessoa;

import java.util.ArrayList;

public class CadastroLivroActivity extends AppCompatActivity {

    private Biblioteca biblioteca;
    private EditText titulo;
    private EditText autor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_livro);
        titulo = findViewById(R.id.editText);
        autor = findViewById(R.id.editText2);
        biblioteca = Biblioteca.getInstance();
    }

    public void salvarLivro(View view){
        String t = titulo.getText().toString();
        String a = autor.getText().toString();
        String r;

        if(!t.isEmpty() && !a.isEmpty()){
            biblioteca.addLivro(new Livro(t,a));
            r = feedbackSys(true);
        }else{
            r = feedbackSys(false);
        }

        Context contexto = getApplicationContext();
        int duracao = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(contexto, r,duracao);
        toast.show();

        titulo.setText("");
        autor.setText("");

    }

    private String feedbackSys(boolean salvou){
        return salvou?"Salvo com sucesso":"Erro, tente novamente";
    }

    public void cancelar(View view){
        //voltar para main
        Intent intent = new Intent(this, MainActivity.class);
        setResult(RESULT_OK, intent);
        finish();
    }
}
