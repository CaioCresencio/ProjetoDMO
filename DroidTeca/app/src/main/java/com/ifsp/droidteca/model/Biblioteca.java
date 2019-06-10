package com.ifsp.droidteca.model;

import java.util.ArrayList;

public class Biblioteca {
    private static Biblioteca INSTANCE;
    private ArrayList<Livro> livros;
    private ArrayList<Pessoa> pessoas;


    private Biblioteca(){
        setLivros(new ArrayList<Livro>());
        setPessoas(new ArrayList<Pessoa>());
    }

    public static synchronized Biblioteca getInstance(){
        if(INSTANCE == null){
            INSTANCE = new Biblioteca();
        }
        return INSTANCE;
    }

    public ArrayList<Livro> getLivros() {
        return livros;
    }

    public void setLivros(ArrayList<Livro> livros) {
        this.livros = livros;
    }

    public ArrayList<Pessoa> getPessoas() {
        return pessoas;
    }

    public void setPessoas(ArrayList<Pessoa> pessoas) {
        this.pessoas = pessoas;
    }

    public boolean emprestar(String titulo, String nomePessoa){
        boolean emprestou = false;
        Pessoa p = getPessoaPeloNome(nomePessoa);
        int posicao = getPosicaoLivro(titulo);
        if(posicao !=-1) {
            Livro l = livros.get(posicao);
            if (p.podeEmprestar() && !l.isEmprestado()) {
                if (l.empresta(p)) {
                    p.empresta();
                    l.empresta(p);
                    emprestou = true;
                }
            }
        }
        return emprestou;
    }

    public boolean devolver(String nome, String titulo ){
        boolean deuCerto = false;
        if(nome != null && titulo != null) {
            Pessoa p = getPessoaPeloNome(nome);
            Livro l = livros.get(getPosicaoLivroEmprestado(titulo));
            if (p != null && l != null) {
                l.setEmprestado(false);
                l.setPessoa(null);
                p.devolve();
                deuCerto = true;
            }
        }
        return deuCerto;
    }

    private int getPosicaoLivro(String titulo){
        for(int i = 0; i < livros.size(); i++){
            Livro l = livros.get(i);
            if(titulo.equals(l.getTitulo()) && !l.isEmprestado()) {
                return i;
            }

        }
        return -1;
    }

    private int getPosicaoLivroEmprestado(String titulo){
        for(int i = 0; i < livros.size(); i++){
            Livro l = livros.get(i);
            if(titulo.equals(l.getTitulo()) && l.isEmprestado()) {
                return i;
            }

        }
        return -1;
    }


    private Pessoa getPessoaPeloNome(String nome){
        Pessoa p = null;

        for(Pessoa pessoa: pessoas){
            if(pessoa.getNome().equals(nome)){
                p = pessoa;
                break;
            }
        }

        return p;
    }


    public void addLivro(Livro l){
        livros.add(l);
    }

    public boolean addPessoa(Pessoa p){
        if(getPessoaPeloNome(p.getNome()) == null) {
           return pessoas.add(p);
        }

        return false;
    }
    public ArrayList<Livro> getLivrosEmprestados(){
        ArrayList<Livro> emprestados = new ArrayList<>();
        for(Livro l: livros){
            if(l.isEmprestado()){
                emprestados.add(l);
            }
        }
        return emprestados;
    }


}
