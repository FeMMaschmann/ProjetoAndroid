package com.inf3m171.fernando.projetoandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;

public class CadConsultaActivity extends AppCompatActivity {

    private EditText etCadNome, etProblema;

    private Spinner spHorario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_consulta);

        etCadNome = (EditText) findViewById(R.id.etCadNome);
        etProblema = (EditText) findViewById(R.id.etProblema);

        spHorario = (Spinner) findViewById(R.id.spHorario);



    }
}
