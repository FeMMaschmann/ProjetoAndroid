package com.inf3m171.fernando.projetoandroid.model;

/**
 * Created by android on 26/09/2018.
 */

public class Paciente {
    private String id, nome, problema, horario;
    private int idade;

    @Override
    public String toString() {
        return nome + " - " + idade + " anos";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getProblema() {
        return problema;
    }

    public void setProblema(String problema) {
        this.problema = problema;
    }
}
