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

public class CadastroPessoaActivity extends AppCompatActivity {

    private Biblioteca biblioteca;
    private EditText nome;
    private EditText qtd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_pessoa);
        nome = findViewById(R.id.editText);
        qtd = findViewById(R.id.editText2);
        biblioteca = Biblioteca.getInstance();
    }

    public void salvarPessoa(View view){
        String n = nome.getText().toString();

        boolean deuCerto = false;

        if(!n.isEmpty() && !qtd.getText().toString().isEmpty()) {
            int qt = Integer.parseInt(qtd.getText().toString());
            deuCerto = biblioteca.addPessoa(new Pessoa(n, qt));
        }


        Context contexto = getApplicationContext();
        String texto = feedbackSys(deuCerto);
        int duracao = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(contexto, texto,duracao);
        toast.show();

        nome.setText("");
        qtd.setText("");

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
