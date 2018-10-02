package com.inf3m171.fernando.projetoandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.inf3m171.fernando.projetoandroid.model.Paciente;

import java.util.ArrayList;
import java.util.List;

public class CadConsultaActivity extends AppCompatActivity {

    private EditText etCadNome, etCadIdade, etProblema;
    private Spinner spHorario;
    private Button btnVoltarCad, btnMarcarCad;




    private FirebaseDatabase database;
    private DatabaseReference reference;

    private ArrayAdapter adapter;

    private Paciente paciente;
    private String acao;

    private String[] horarios ={"Select", "9:00", "9:50", "10:40", "11:20", "12:00", "14:30", "15:20", "16:10", "17:00"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_consulta);

        etCadNome = (EditText) findViewById(R.id.etCadNome);
        etCadIdade = (EditText) findViewById(R.id.etCadIdade);
        etProblema = (EditText) findViewById(R.id.etProblema);

        spHorario = (Spinner) findViewById(R.id.spHorario);

        btnVoltarCad = (Button) findViewById(R.id.btnVoltarCad);
        btnMarcarCad = (Button) findViewById(R.id.btnMarcarCad);

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, horarios);
        spHorario.setAdapter(adapter);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        btnVoltarCad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CadConsultaActivity.this, ListaActivity.class);
                startActivity(i);
            }
        });

        btnMarcarCad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastrar();
            }
        });

        acao = getIntent().getExtras().getString("acao");

        if (acao.equals("editar")) {
            paciente = new Paciente();
            paciente.setId(getIntent().getExtras().getString("idPaciente"));
            paciente.setNome(getIntent().getExtras().getString("nomePaciente"));
            paciente.setIdade(getIntent().getExtras().getString("idadePaciente"));
            paciente.setProblema(getIntent().getExtras().getString("problemaPaciente"));
            //paciente.setHorario(getIntent().getExtras().getInt("horarioPaciente"));

            String nomePaciente = getIntent().getExtras().getString("nomePaciente");
            String idadePaciente = getIntent().getExtras().getString("idadePaciente");
            String problemaPaciente = getIntent().getExtras().getString("problemaPaciente");
            String horarioPaciente = getIntent().getExtras().getString("horarioPaciente");
            etCadNome.setText(nomePaciente);
            etCadIdade.setText(idadePaciente);
            etProblema.setText(problemaPaciente);
        }
    }


    private void cadastrar(){
        String nome = etCadNome.getText().toString();
        String idade = etCadIdade.getText().toString();
        String problema = etProblema.getText().toString();
        String horario = spHorario.getSelectedItem().toString();

        if (nome.isEmpty() || idade.isEmpty() || problema.isEmpty() || spHorario.getSelectedItemPosition() == 0) {
            Toast.makeText(this, getResources().getString(R.string.txtAviso), Toast.LENGTH_SHORT).show();
        }else{

            if (acao.equals("inserir")) {
                paciente = new Paciente();
            }else {
                paciente.setId(getIntent().getExtras().getString("idPaciente"));
            }
            paciente.setNome(nome);
            paciente.setIdade(idade);
            paciente.setProblema(problema);
            paciente.setHorario(horario);

            if (acao.equals("inserir"))
                reference.child("pacientes").push().setValue(paciente);
//            }else if (acao.equals("editar")) {
//                reference.child("pacientes").getParent().child(paciente.getId()).getParent().setValue("paciente");
//            }



            Toast.makeText(this, getResources().getString(R.string.txtConsultaOk), Toast.LENGTH_LONG).show();

            finish();
        }
    }
}
