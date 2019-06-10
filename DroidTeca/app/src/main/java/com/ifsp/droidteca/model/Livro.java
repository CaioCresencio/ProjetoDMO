package com.ifsp.droidteca.model;

public class Livro  {
    private int id;
    private String titulo;
    private String autor;
    private boolean emprestado;
    private Pessoa pessoa;

    public Livro(String titulo, String autor){
        setTitulo(titulo);
        setAutor(autor);
        setEmprestado(false);
        setPessoa(null);
    }

    public Livro(int id, String titulo, String autor, Pessoa p){
        setId(id);
        setTitulo(titulo);
        setAutor(autor);
        setEmprestado(false);
        setPessoa(p);
    }

    public boolean empresta(Pessoa p){
        if(p.podeEmprestar() && !emprestado){
            this.emprestado = true;
            this.pessoa = p;
            return true;
        }
        return false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public boolean isEmprestado() {
        return pessoa!=null?true:false;
    }

    public void setEmprestado(boolean emprestado) {
        this.emprestado = emprestado;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Titulo: ");
        sb.append(titulo);
        sb.append("\n");
        sb.append("Autor: ");
        sb.append(autor);
        return sb.toString();
    }
}
