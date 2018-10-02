package com.inf3m171.fernando.projetoandroid.model;

/**
 * Created by android on 28/09/2018.
 */

public class Horario {
    private String id;
    private String horario;

    @Override
    public String toString() {
        return id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }
}
