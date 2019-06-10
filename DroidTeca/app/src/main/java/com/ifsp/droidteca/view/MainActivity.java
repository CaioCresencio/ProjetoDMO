package com.ifsp.droidteca.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.ifsp.droidteca.Dao.BancoBibliotecaDAO;
import com.ifsp.droidteca.R;
import com.ifsp.droidteca.model.Biblioteca;
import com.ifsp.droidteca.model.Livro;
import com.ifsp.droidteca.model.Pessoa;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  implements AdapterView.OnItemSelectedListener {

    private Biblioteca biblioteca;
    private ArrayList<Livro> listaLivros;
    private ArrayList<Pessoa> listaPessoas;
    private Spinner spinnerLivro;
    private Spinner spinnerPessoa;
    private ArrayList<String> livrosStr;
    private ArrayList<String> pessoasStr;
    private ArrayAdapter<String> dataAdapterLivro;
    private ArrayAdapter<String> dataAdapterPessoa;
    private String titulo;
    private String nomePessoa;
    private BancoBibliotecaDAO bancoBibliotecaDAO;
    private int indexLivro;
    private int indexPessoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        biblioteca = Biblioteca.getInstance();
        bancoBibliotecaDAO = new BancoBibliotecaDAO(this);

        bancoBibliotecaDAO.abrir();
        biblioteca.setPessoas(bancoBibliotecaDAO.getPessoas());
        biblioteca.setLivros(bancoBibliotecaDAO.getLivros());
        bancoBibliotecaDAO.fechar();

        iniciarAPP();

    }

    private void iniciarAPP(){
        bancoBibliotecaDAO.abrir();
        bancoBibliotecaDAO.deletar();
        bancoBibliotecaDAO.fechar();


        listaPessoas = biblioteca.getPessoas();
        listaLivros = biblioteca.getLivros();

        bancoBibliotecaDAO.abrir();

        bancoBibliotecaDAO.addPessoas(listaPessoas);
        bancoBibliotecaDAO.addLivros(listaLivros);

        bancoBibliotecaDAO.fechar();


        biblioteca.setPessoas(bancoBibliotecaDAO.getPessoas());
        biblioteca.setLivros(bancoBibliotecaDAO.getLivros());
        bancoBibliotecaDAO.fechar();


        spinnerLivro = (Spinner) findViewById(R.id.spinnerLivro);
        spinnerLivro.setOnItemSelectedListener(this);

        carregaLivrosSpinner();

        spinnerPessoa = (Spinner) findViewById(R.id.spinnerPessoa);
        spinnerPessoa.setOnItemSelectedListener(this);

        carregaPessoasSpinner();

        atualizaSpinner();
    }


    @Override
    public void onPause() {
        super.onPause();
        bancoBibliotecaDAO.salvarEstado();
    }

    @Override
    public void onStop() {
        super.onStop();
        bancoBibliotecaDAO.salvarEstado();
    }
    @Override
    public void onResume() {
        super.onResume();
        iniciarAPP();
    }

    private void atualizaSpinner(){
        dataAdapterLivro = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, livrosStr);
        dataAdapterLivro.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLivro.setAdapter(dataAdapterLivro);


        dataAdapterPessoa = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, pessoasStr);
        dataAdapterPessoa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPessoa.setAdapter(dataAdapterPessoa);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if(parent.equals(spinnerLivro)) {
            titulo = parent.getItemAtPosition(position).toString();
            indexLivro = position;

        }else{
            nomePessoa = parent.getItemAtPosition(position).toString();
            indexPessoa = position;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.action_cadastroLivro) {
            Intent intent = new Intent(this, CadastroLivroActivity.class);
            startActivityForResult(intent, 1);
        }else if(id == R.id.action_cadastroPessoa){
            Intent intent = new Intent(this, CadastroPessoaActivity.class);
            startActivityForResult(intent, 1);
        }else if(id == R.id.action_emprestimo){
            Intent intent = new Intent(this, ListarEmp.class);
            startActivity(intent);
        }else if(id == R.id.action_livros){
            Intent intent = new Intent(this, ListarLivros.class);
            startActivity(intent);
        }else if(id == R.id.action_devolver){
            Intent intent = new Intent(this, Devolucao.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void realizarEmprestimo(View view){

        boolean emprestou = biblioteca.emprestar(titulo, nomePessoa);

        carregaPessoasSpinner();
        carregaLivrosSpinner();

        atualizaSpinner();

        Context contexto = getApplicationContext();
        String texto = feedbackSys(emprestou);
        int duracao = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(contexto, texto,duracao);
        toast.show();

    }

    private String feedbackSys(boolean emprestou){
        return emprestou?"Empréstimo efetuado":"Empréstimo não efetuado";
    }


    private void carregaLivrosSpinner(){
        ArrayList<Livro> livros = biblioteca.getLivros();
        livrosStr = new ArrayList<String>();

        for(Livro l: livros){
            if(!l.isEmprestado()) {
                livrosStr.add(l.getTitulo());
            }
        }
    }

    private void carregaPessoasSpinner(){
        ArrayList<Pessoa> pessoas = biblioteca.getPessoas();
        pessoasStr = new ArrayList<String>();

        for(Pessoa p: pessoas) {
            if (p.podeEmprestar()) {
                pessoasStr.add(p.getNome());
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                carregaPessoasSpinner();
                carregaLivrosSpinner();

                atualizaSpinner();
            }

        }
    }
}
