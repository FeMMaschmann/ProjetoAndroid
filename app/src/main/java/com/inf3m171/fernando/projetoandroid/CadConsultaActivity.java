package com.inf3m171.fernando.projetoandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.inf3m171.fernando.projetoandroid.model.Paciente;

import java.util.HashMap;

public class CadConsultaActivity extends AppCompatActivity {

    private EditText etCadNome, etCadIdade, etProblema;
    private Spinner spHorario;
    private Button btnVoltarCad, btnMarcarCad;

    private FirebaseDatabase database;
    private DatabaseReference reference;

    private ArrayAdapter adapter;

    private Paciente paciente;
    private String acao;

    private String[] horarios ={getResources().getString(R.string.selecione), "9:00", "9:50", "10:40", "11:20", "12:00", "14:30", "15:20", "16:10", "17:00"};

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
            paciente.setHorario(getIntent().getExtras().getString("horarioPaciente"));

            String nomePaciente = getIntent().getExtras().getString("nomePaciente");
            String idadePaciente = getIntent().getExtras().getString("idadePaciente");
            String problemaPaciente = getIntent().getExtras().getString("problemaPaciente");
            String horarioPaciente = getIntent().getExtras().getString("horarioPaciente");
            etCadNome.setText(nomePaciente);
            etCadIdade.setText(idadePaciente);
            etProblema.setText(problemaPaciente);

            for (int i = 1; i < horarios.length; i++){
                if (horarios[i].equals(horarioPaciente)){
                    spHorario.setSelection(i);
                    break;
                }
            }

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

            if (acao.equals("inserir")){
                reference.child("pacientes").push().setValue(paciente);
            }else {
                HashMap<String, Object> nomeUp = new HashMap<>();
                nomeUp.put("nome", paciente.getNome());
                FirebaseDatabase.getInstance().getReference().child("pacientes").child(paciente.getId()).updateChildren(nomeUp);

                HashMap<String, Object> idadeUp = new HashMap<>();
                idadeUp.put("idade", paciente.getIdade());
                FirebaseDatabase.getInstance().getReference().child("pacientes").child(paciente.getId()).updateChildren(idadeUp);

                HashMap<String, Object> problemaUp = new HashMap<>();
                problemaUp.put("problema", paciente.getProblema());
                FirebaseDatabase.getInstance().getReference().child("pacientes").child(paciente.getId()).updateChildren(problemaUp);

                HashMap<String, Object> horarioUp = new HashMap<>();
                horarioUp.put("horario", paciente.getHorario());
                FirebaseDatabase.getInstance().getReference().child("pacientes").child(paciente.getId()).updateChildren(horarioUp);
            }



            Toast.makeText(this, getResources().getString(R.string.txtConsultaOk), Toast.LENGTH_LONG).show();

            finish();
        }
    }
}
