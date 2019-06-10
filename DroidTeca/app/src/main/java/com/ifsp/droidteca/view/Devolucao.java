package com.ifsp.droidteca.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

public class Devolucao extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Biblioteca biblioteca;
    private ArrayList<Livro> listaLivros;
    private ArrayList<Pessoa> listaPessoas;
    private Spinner spinnerLivro;
    private Spinner spinnerPessoa;
    private ArrayList<String> livrosStr;
    private ArrayList<String> pessoasStr;
    private ArrayAdapter<String> dataAdapterLivro;
    private ArrayAdapter<String> dataAdapterPessoa;
    private BancoBibliotecaDAO bancoBibliotecaDAO;
    private String titulo;
    private String nomePessoa;
    private int indexLivro;
    private int indexPessoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devolver);
        iniciar();
    }
    private void iniciar(){
        biblioteca = Biblioteca.getInstance();
        bancoBibliotecaDAO = new BancoBibliotecaDAO(this);

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

    private void carregaLivrosSpinner(){
        ArrayList<Livro> livros = biblioteca.getLivros();
        livrosStr = new ArrayList<String>();

        for(Livro l: livros){
            if(l.isEmprestado() && l.getPessoa().getNome().equals(nomePessoa)) {
                livrosStr.add(l.getTitulo());
            }
        }
        atualizarSpinnerLivro();

    }

    private void carregaPessoasSpinner(){
        ArrayList<Livro> livros = biblioteca.getLivros();
        pessoasStr = new ArrayList<String>();

        for(Livro l: livros) {
            if (l.getPessoa() != null && !existePessoa(l.getPessoa().getNome()))  {
                pessoasStr.add(l.getPessoa().getNome());
            }
        }
    }
    private boolean existePessoa(String pessoa){
        boolean existe = false;
        for(int i = 0; i<pessoasStr.size();i++){
            if(pessoasStr.get(i).equals(pessoa)){
                existe = true;
            }
        }
        return existe;
    }


    private void atualizaSpinner(){
        dataAdapterLivro = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, livrosStr);
        dataAdapterLivro.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLivro.setAdapter(dataAdapterLivro);


        dataAdapterPessoa = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, pessoasStr);
        dataAdapterPessoa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPessoa.setAdapter(dataAdapterPessoa);
    }
    private void atualizarSpinnerLivro(){
        dataAdapterLivro = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, livrosStr);
        dataAdapterLivro.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLivro.setAdapter(dataAdapterLivro);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.equals(spinnerLivro)) {
            titulo = parent.getItemAtPosition(position).toString();
            indexLivro = position;

        }else{
            nomePessoa = parent.getItemAtPosition(position).toString();
            indexPessoa = position;
            carregaLivrosSpinner();
        }
    }

    public void realizarDevolucao(View view){

        boolean devolveu = biblioteca.devolver(nomePessoa, titulo);

        carregaPessoasSpinner();
        carregaLivrosSpinner();

        atualizaSpinner();

        Context contexto = getApplicationContext();
        String texto = feedbackSys(devolveu);
        int duracao = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(contexto, texto,duracao);
        toast.show();
        if(devolveu){
            nomePessoa = null;
            titulo = null;
        }

    }

    private String feedbackSys(boolean devolveu){
        return devolveu?"Devolução efetuada":"Devolução não efetuado";
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        nomePessoa = null;
        titulo = null;
    }
}
