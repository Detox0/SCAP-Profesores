package com.usach.sebastianvallejos.scapprofesores.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.usach.sebastianvallejos.scapprofesores.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ColegiosActivity extends AppCompatActivity {

    //Variables a utilizar
    private FirebaseDatabase mDataBase = FirebaseDatabase.getInstance();
    private Intent intent;
    private String colegio;
    private String correo;
    private Button boton;
    private Spinner spinnerColegios;
    private List<String> colegios = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colegios);

        //Obtenemos el intent actual
        intent = getIntent();

        //Buscamos los elementos en la vista por su ID
        boton = (Button) findViewById(R.id.boton_colegios);
        spinnerColegios = (Spinner) findViewById(R.id.spinner_colegios_2);

        obtenerDatosAnteriores();
        setearListener();
    }

    private void obtenerDatosAnteriores()
    {
        correo = intent.getStringExtra("correo");
        obtenerColegio();
    }

    //Recuperar los colegios registrados en la app
    private void obtenerColegio()
    {
        DatabaseReference colegiosRef = mDataBase.getReference();

        colegiosRef.child("colegios").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot data: dataSnapshot.getChildren())
                {
                    String cole = data.getKey().toString();
                    colegios.add(cole);
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ColegiosActivity.this, android.R.layout.simple_spinner_item, colegios);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerColegios.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setearListener()
    {
        //Listener para el boton, le otorga una accion al mismo
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                colegio = spinnerColegios.getSelectedItem().toString();

                if(spinnerColegios.getSelectedItem() != null && colegio != null)
                {
                    Intent menuIntent = new Intent(ColegiosActivity.this, MainMenuActivity.class);

                    menuIntent.putExtra("colegio",colegio);
                    menuIntent.putExtra("correo", correo);

                    startActivity(menuIntent);
                }else{
                    Toast.makeText(ColegiosActivity.this,"No se ha seleccionado un colegio, porfavor seleccione uno.",Toast.LENGTH_LONG);
                }
            }
        });
    }
}
