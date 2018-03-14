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
        cargarDatosBD();
    }

    private void cargarDatosBD()
    {
        DatabaseReference colegiosRef = mDataBase.getReference("colegios");

        colegiosRef.orderByKey().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                colegios.add(dataSnapshot.getKey().toString());
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
        });

        rellenarSpinner();
    }

    //Una vez obtenidos los colegios, se procede a rellenar el spinner con su informacion
    private void rellenarSpinner()
    {
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.support_simple_spinner_dropdown_item,colegios);

        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerColegios.setAdapter(spinnerArrayAdapter);
        spinnerArrayAdapter.notifyDataSetChanged();

        spinnerColegios.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                colegio = spinnerColegios.getSelectedItem().toString();
                Log.i("PRUEBA","TRIGGERED");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.i("PRUEBA", "Nothing selected");
            }
        });
        Log.i("PRUEBA", "Done");
    }

    private void setearListener()
    {
        //Listener para el boton, le otorga una accion al mismo
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
