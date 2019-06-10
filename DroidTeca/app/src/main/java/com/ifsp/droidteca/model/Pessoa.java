package com.ifsp.droidteca.model;

public class Pessoa {
    private String nome;
    private int qtdEmp;

    public Pessoa(String nome, int qtdEmp){
        this.setNome(nome);
        setQtdEmp(qtdEmp);
    }

    public void empresta(){
        setQtdEmp(getQtdEmp()-1);
    }
    public void devolve(){
        setQtdEmp(getQtdEmp()+1);
    }


    public boolean podeEmprestar(){
        if(getQtdEmp() <= 0){
            return false;
        }
        return true;
    }
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQtdEmp() {
        return qtdEmp;
    }

    public void setQtdEmp(int qtdEmp) {
        this.qtdEmp = qtdEmp;
    }
}
