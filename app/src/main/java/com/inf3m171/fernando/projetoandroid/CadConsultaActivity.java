package com.inf3m171.fernando.projetoandroid;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.inf3m171.fernando.projetoandroid.model.Horario;
import com.inf3m171.fernando.projetoandroid.model.Paciente;

import java.util.ArrayList;
import java.util.List;

public class CadConsultaActivity extends AppCompatActivity {

    private EditText etCadNome, etCadIdade, etProblema;
    private Spinner spHorario;
    private Button btnVoltarCad, btnMarcarCad;

    private List<Horario> listaDeHorario;

    private FirebaseDatabase database;
    private DatabaseReference reference;

    private ArrayAdapter adapter;
    private Query queryRef;
    private ChildEventListener childEventListener;

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

        listaDeHorario = new ArrayList<>();
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
            paciente.setIdade(getIntent().getExtras().getInt("idadePaciente"));
            paciente.setProblema(getIntent().getExtras().getString("problemaPaciente"));
            //paciente.setHorario(getIntent().getExtras().getInt("horarioPaciente"));

            String nomePaciente = getIntent().getExtras().getString("nomePaciente");
            int idadePaciente = getIntent().getExtras().getInt("idadePaciente");
            String problemaPaciente = getIntent().getExtras().getString("problemaPaciente");
            String horarioPaciente = getIntent().getExtras().getString("horarioPaciente");
            etCadNome.setText(nomePaciente);
            etCadIdade.setText(idadePaciente);
            etProblema.setText(problemaPaciente);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        queryRef = reference.child("horarios").orderByKey();

        listaDeHorario.clear();

        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Horario hora = new Horario();
                hora.setId(dataSnapshot.getKey());
                hora.setHorario(dataSnapshot.child("horarios").getValue(String.class));
                listaDeHorario.add(hora);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        queryRef.addChildEventListener(childEventListener);
    }

    @Override
    protected void onStop() {
        super.onStop();

        queryRef.removeEventListener(childEventListener);
    }

    private void informacaoPaciente(){
        String nomePaciente = getIntent().getExtras().getString("nomePaciente");
        int idadePaciente = getIntent().getExtras().getInt("idadePaciente");
        String problemaPaciente = getIntent().getExtras().getString("problemaPaciente");
        //String horarioPaciente = getIntent().getExtras().getString("horarioPaciente");
        etCadNome.setText(nomePaciente);
        etCadIdade.setText(idadePaciente);
        etProblema.setText(problemaPaciente);
        //spHorario.set(horarioPaciente);
    }

    private void cadastrar(){
        String nome = etCadNome.getText().toString();
        String idade = etCadIdade.getText().toString();
        String problema = etProblema.getText().toString();
        String horario = spHorario.getSelectedItem().toString();

        if (nome.isEmpty() || idade.isEmpty() || problema.isEmpty() || spHorario.getSelectedItemPosition() == 0) {
            Toast.makeText(this, getResources().getString(R.string.txtAviso), Toast.LENGTH_SHORT).show();
        }else{

            Horario hora = new Horario();
            hora.getHorario();
            hora.getId();

            if (acao.equals("inserir")) {
                paciente = new Paciente();
            }
            paciente.setNome(nome);
            paciente.setIdade(Integer.valueOf(idade));
            paciente.setProblema(problema);
            paciente.setHorario(hora);

            reference.child("pacientes").push().setValue(paciente);

            Toast.makeText(this, getResources().getString(R.string.txtConsultaOk), Toast.LENGTH_LONG).show();

            finish();
        }
    }
}
