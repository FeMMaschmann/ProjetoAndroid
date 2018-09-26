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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.inf3m171.fernando.projetoandroid.model.Paciente;

public class CadConsultaActivity extends AppCompatActivity {

    private EditText etCadNome, etCadIdade, etProblema;
    private Spinner spHorario;
    private Button btnVoltarCad, btnMarcarCad;

    private FirebaseDatabase database;
    private DatabaseReference reference;

    private ArrayAdapter adapter;
    private String[] Horarios ={ "Selecione...", "9:00", "9:45", "10:30", "11:15"};

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

        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        adapter = new ArrayAdapter( this ,
                android.R.layout.simple_list_item_1, Horarios);
        spHorario.setAdapter(adapter);

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
    }

    private void cadastrar(){
        String nome = etCadNome.getText().toString();
        String idade = etCadIdade.getText().toString();
        String problema = etProblema.getText().toString();

        if (nome.isEmpty() || idade.isEmpty() || problema.isEmpty() || spHorario.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Todos os campos devem ser preenchidos!", Toast.LENGTH_SHORT).show();
        }else{
            Paciente paciente = new Paciente();
            paciente.setNome(nome);
            paciente.setIdade(Integer.valueOf(idade));
            paciente.setProblema(problema);
            paciente.setHorario(spHorario.getSelectedItem().toString());

            reference.child("pacientes").push().setValue(paciente);

            Toast.makeText(this, "Consulta marcada com sucesso, lhe contataremos a data pelo e-mail!", Toast.LENGTH_LONG);
        }
    }
}
