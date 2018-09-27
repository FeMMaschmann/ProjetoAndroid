package com.inf3m171.fernando.projetoandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.inf3m171.fernando.projetoandroid.model.Paciente;

import java.util.ArrayList;
import java.util.List;

public class ListaActivity extends AppCompatActivity {

    private ListView lvLista;
    private List<Paciente> listaDePacientes;
    private ArrayAdapter adapter;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private Query queryRef;
    private ChildEventListener childEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lvLista = (ListView) findViewById(R.id.lvLista);
        listaDePacientes = new ArrayList<>();
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listaDePacientes);
        lvLista.setAdapter(adapter);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ListaActivity.this, CadConsultaActivity.class);
                startActivity(i);
            }
        });


        lvLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                AlertDialog.Builder alerta = new AlertDialog.Builder(ListaActivity.this);
                alerta.setTitle("Informação do paciente");
                alerta.setIcon(android.R.drawable.ic_menu_edit);
                alerta.setMessage("Confira as informações do(a) paciente: " + listaDePacientes.get(i));


                alerta.show();
            }
        });




    }

    @Override
    protected void onStart() {
        super.onStart();

        queryRef = reference.child("pacientes").orderByKey();

        listaDePacientes.clear();

        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Paciente paciente = new Paciente();
                paciente.setId(dataSnapshot.getKey());
                paciente.setNome(dataSnapshot.child("nome").getValue(String.class));
                paciente.setIdade(dataSnapshot.child("idade").getValue(Integer.class));
                paciente.setProblema(dataSnapshot.child("problema").getValue(String.class));
                paciente.setHorario(dataSnapshot.child("horario").getValue(String.class));
                listaDePacientes.add(paciente);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add("Sair");

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.toString().equals("Sair")){
            FirebaseAuth.getInstance().signOut();
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
